package cn.irving.zhao.platform.weixin.mp;

import cn.irving.zhao.platform.weixin.mp.config.WeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.config.impl.DefaultWeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.token.AccessTokenManager;
import cn.irving.zhao.platform.weixin.mp.token.impl.DefaultAccessTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
    //TODO 添加  transient static 配置区域

    private Logger logger = LoggerFactory.getLogger(WeChartMpClient.class);
    /**
     * 微信配置文件
     */
    private String propertyPath = "/conf/wx.client.properties";

    private AccessTokenManager tokenManager;
    private WeChartConfigManager configManager;

    //微信公众号配置文件
    //AccessTokenManager类
    //WeChartMpConfigManager类
    //构建manager对象，初始化配置信息、初始化token
    //  token刷新器的实现、配置以及默认实现

    /**
     * <p>初始化微信账户管理器、token管理器</p>
     * <p>当账户管理器或token管理器为null时，采用配置文件形式进行账户管理器与token管理器实例化</p>
     */
    public void init() {
        if (this.configManager == null || this.tokenManager == null) {
            loadProperties();
        }
        this.configManager.init();
        this.tokenManager.init(this.configManager);
    }

    /**
     * <p>加载配置文件，实例化微信账户管理器、token管理器</p>
     * <p>配置文件地址默认为：/conf/wx.client.properties</p>
     */
    public void loadProperties() {
        try {
            Properties properties = new Properties();
            properties.load(WeChartMpClient.class.getResourceAsStream(propertyPath));//TODO 读取相关配置文件，配置方式变更
            try {
                //初始化configManager
                configManager:
                if (this.configManager == null) {
                    String configManagerClassName = properties.getProperty("wx.client.configManager");
                    Class<?> configManagerClass;
                    if (configManagerClassName == null || configManagerClassName.equals("")) {
                        this.configManager = new DefaultWeChartConfigManager();
                        logger.warn("配置文件中不包含wx.client.configManager项，configManager使用默认值");
                        break configManager;
                    }
                    configManagerClass = Class.forName(configManagerClassName);
                    if (!WeChartConfigManager.class.isAssignableFrom(configManagerClass)) {
                        this.configManager = new DefaultWeChartConfigManager();
                        logger.warn(configManagerClassName + "不是一个有效的cn.irving.zhao.util.weChart.mp.config.WeChartConfigManager类型");
                        break configManager;
                    }
                    this.configManager = (WeChartConfigManager) configManagerClass.newInstance();
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                logger.warn("tokenManager初始化失败，将使用默认值");
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
                        logger.warn("配置文件中不包含wx.client.tokenManager，tokenManager使用默认值");
                        break tokenManager;
                    }
                    tokenManagerClass = Class.forName(tokenManagerClassName);
                    if (!AccessTokenManager.class.isAssignableFrom(tokenManagerClass)) {
                        this.tokenManager = new DefaultAccessTokenManager();
                        logger.warn(tokenManagerClassName + "不是一个有效的cn.irving.zhao.util.weChart.mp.config.AccessTokenManager类型");
                        break tokenManager;
                    }
                    this.tokenManager = (AccessTokenManager) tokenManagerClass.newInstance();
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                logger.warn("tokenManager初始化失败，将使用默认值");
                this.tokenManager = new DefaultAccessTokenManager();
            }

        } catch (IOException e) {
            this.tokenManager = new DefaultAccessTokenManager();
            this.configManager = new DefaultWeChartConfigManager();
            logger.warn("客户端配置文件加载异常，启用默认配置", e);
        }
    }

    public static void sendMessage() {
        //TODO SendMessage
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }
}
