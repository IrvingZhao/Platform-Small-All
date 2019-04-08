package cn.irving.zhao.platform.weixin.mp;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendInputMessage;
import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import cn.irving.zhao.platform.weixin.base.message.send.MessageSender;
import cn.irving.zhao.platform.weixin.mp.config.WeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.config.WeChartMpConfig;
import cn.irving.zhao.platform.weixin.mp.config.impl.DefaultWeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.entity.RedirectUrlParam;
import cn.irving.zhao.platform.weixin.mp.entity.UserInfo;
import cn.irving.zhao.platform.weixin.mp.entity.UserTokenInfo;
import cn.irving.zhao.platform.weixin.mp.entity.message.UserCodeToTokenInputMessage;
import cn.irving.zhao.platform.weixin.mp.entity.message.UserCodeToTokenOutputMessage;
import cn.irving.zhao.platform.weixin.mp.entity.message.UserInfoInputMessage;
import cn.irving.zhao.platform.weixin.mp.entity.message.UserInfoOutputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.token.AccessTokenManager;
import cn.irving.zhao.platform.weixin.mp.token.impl.DefaultAccessTokenManager;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 微信公众号客户端
 * <p>初始化读取propertyPath中的配置文件内容，默认值为classpath:/conf/wx.client.properties</p>
 * <p>配置项包括</p>
 * <ul>
 * <li>wx.client.tokenManager   token管理器类全名，默认为：{@link DefaultAccessTokenManager}</li>
 * <li>wx.client.configManager  微信账号配置管理器类全名，默认为：{@link DefaultWeChartConfigManager}</li>
 * </ul>
 */
public final class WeChartMpClient {
    private static final String AUTH_USER_REDIRECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=message&scope=%s&state=%s#wechat_redirect";

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChartMpClient.class);
    /**
     * 微信配置文件
     */
    private String propertyPath = "/conf/wx.client.properties";

    private final MessageSender messageSender = new MessageSender();

    @Setter
    @Getter
    private AccessTokenManager tokenManager;
    @Setter
    @Getter
    private WeChartConfigManager configManager;

    private boolean init = false;

    //微信公众号配置文件
    //AccessTokenManager类
    //WeChartMpConfigManager类
    //构建manager对象，初始化配置信息、初始化token
    //  token刷新器的实现、配置以及默认实现

    /**
     * <p>初始化微信账户管理器、token管理器</p>
     * <p>当账户管理器或token管理器为null时，采用配置文件形式进行账户管理器与token管理器实例化</p>
     */
    public synchronized void init() {
        if (!init) {
            if (this.configManager == null || this.tokenManager == null) {
                loadProperties();
            }
            this.configManager.init();
            this.tokenManager.init(this.configManager, this::sendMessage);
        }
        init = true;
    }

    /**
     * <p>加载配置文件，实例化微信账户管理器、token管理器</p>
     * <p>配置文件地址默认为：/conf/wx.client.properties</p>
     */
    public void loadProperties() {
        try {
            Properties properties = new Properties();
            properties.load(WeChartMpClient.class.getResourceAsStream(propertyPath));
            try {
                //初始化configManager
                configManager:
                if (this.configManager == null) {
                    String configManagerClassName = properties.getProperty("wx.client.configManager");
                    Class<?> configManagerClass;
                    if (configManagerClassName == null || configManagerClassName.equals("")) {
                        this.configManager = new DefaultWeChartConfigManager();
                        LOGGER.warn("配置文件中不包含wx.client.configManager项，configManager使用默认值");
                        break configManager;
                    }
                    configManagerClass = Class.forName(configManagerClassName);
                    if (!WeChartConfigManager.class.isAssignableFrom(configManagerClass)) {
                        this.configManager = new DefaultWeChartConfigManager();
                        LOGGER.warn(configManagerClassName + "不是一个有效的cn.irving.zhao.util.weChart.mp.config.WeChartConfigManager类型");
                        break configManager;
                    }
                    this.configManager = (WeChartConfigManager) configManagerClass.getDeclaredConstructor().newInstance();
                }
            } catch (NoSuchMethodException | InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                LOGGER.warn("tokenManager初始化失败，将使用默认值");
                this.configManager = new DefaultWeChartConfigManager();
            }

            try {
                //初始化configManager
                tokenManager:
                if (this.tokenManager == null) {
                    String tokenManagerClassName = properties.getProperty("wx.client.tokenManager");
                    Class<?> tokenManagerClass;
                    if (tokenManagerClassName == null || tokenManagerClassName.equals("")) {
                        this.tokenManager = new DefaultAccessTokenManager();
                        LOGGER.warn("配置文件中不包含wx.client.tokenManager，tokenManager使用默认值");
                        break tokenManager;
                    }
                    tokenManagerClass = Class.forName(tokenManagerClassName);
                    if (!AccessTokenManager.class.isAssignableFrom(tokenManagerClass)) {
                        this.tokenManager = new DefaultAccessTokenManager();
                        LOGGER.warn(tokenManagerClassName + "不是一个有效的cn.irving.zhao.util.weChart.mp.config.AccessTokenManager类型");
                        break tokenManager;
                    }
                    this.tokenManager = (AccessTokenManager) tokenManagerClass.getDeclaredConstructor().newInstance();
                }
            } catch (NoSuchMethodException | InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                LOGGER.warn("tokenManager初始化失败，将使用默认值");
                this.tokenManager = new DefaultAccessTokenManager();
            }

        } catch (IOException e) {
            this.tokenManager = new DefaultAccessTokenManager();
            this.configManager = new DefaultWeChartConfigManager();
            LOGGER.warn("客户端配置文件加载异常，启用默认配置", e);
        }
    }

    /**
     * 发送消息，直接发送，用于无需设置token的消息
     *
     * @param outputMessage 待发送的消息
     * @return 返回消息
     */
    public <T extends BaseSendInputMessage> T sendMessage(BaseSendOutputMessage<T> outputMessage) {
        return this.messageSender.sendMessage(outputMessage);
    }

    /**
     * 发送消息，自动设置token，如果token失效，自动刷新token并重发
     *
     * @param configName    配置名
     * @param outputMessage 待发送的消息
     * @return 消息结果
     */
    public <T extends BaseMpSendInputMessage> T sendMessage(String configName, BaseMpSendOutputMessage<T> outputMessage) {
        if (!this.init) {
            this.init();
        }
        String token = this.tokenManager.getToken(configName);
        outputMessage.setAccessToken(token);
        T inputMessage = this.messageSender.sendMessage(outputMessage);

        if (inputMessage != null) {//添加刷新机制
            if ("40001".equals(inputMessage.getErrCode()) || "40014".equals(inputMessage.getErrCode()) || "42001".equals(inputMessage.getErrCode())) {
                LOGGER.info("token need refresh by message [" + inputMessage.getErrCode() + "], reason [" + inputMessage.getErrMsg() + "]");

                this.tokenManager.refreshToken(configName);
                return sendMessage(configName, outputMessage);
            }
        }
        return inputMessage;
    }

    /**
     * 获取授权访问地址
     *
     * @param configName       配置名
     * @param redirectUrlParam 重定向及授权域配置
     * @return 登录地址
     */
    public String getAuthUrl(String configName, RedirectUrlParam redirectUrlParam) {
        WeChartMpConfig config = this.configManager.getConfig(configName);
        try {
            return String.format(AUTH_USER_REDIRECT_URL,
                    config.getAppId(),
                    URLEncoder.encode(redirectUrlParam.getRedirectUrl(), "UTF-8"),
                    redirectUrlParam.getAuthScope().getCode(),
                    redirectUrlParam.getState());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据授权码，获取用户Token信息
     *
     * @param configName 配置名
     * @param authCode   授权码
     * @return token信息
     */
    public UserTokenInfo getUserTokenByCode(String configName, String authCode) {
        WeChartMpConfig config = this.configManager.getConfig(configName);
        UserCodeToTokenOutputMessage outputMessage = new UserCodeToTokenOutputMessage(
                config.getAppId(), config.getAppSecurity(), authCode
        );
        UserCodeToTokenInputMessage inputMessage = this.sendMessage(outputMessage);

        return new UserTokenInfo(
                inputMessage.getAccessToken(),
                inputMessage.getExpiresIn(),
                inputMessage.getRefreshToken(),
                inputMessage.getOpenId(),
                inputMessage.getScope()
        );
    }

    /**
     * 根据用户token和openId获取用户信息
     *
     * @param token  用户token
     * @param openId 用户openId
     * @return 用户信息
     */
    public UserInfo getUserInfoByTokenAndOpenId(String token, String openId) {
        UserInfoOutputMessage outputMessage = new UserInfoOutputMessage(token, openId);
        UserInfoInputMessage infoInputMessage = this.sendMessage(outputMessage);
        return infoInputMessage.toUserInfo();
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

}
