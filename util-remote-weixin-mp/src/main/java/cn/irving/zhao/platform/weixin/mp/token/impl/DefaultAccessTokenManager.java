package cn.irving.zhao.platform.weixin.mp.token.impl;


import cn.irving.zhao.platform.weixin.mp.WeChartMpClient;
import cn.irving.zhao.platform.weixin.mp.config.WeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.config.WeChartMpConfig;
import cn.irving.zhao.platform.weixin.mp.message.send.token.AccessTokenInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.token.AccessTokenOutputMessage;
import cn.irving.zhao.platform.weixin.mp.token.AccessTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认token管理器
 */
public class DefaultAccessTokenManager implements AccessTokenManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenManager.class);

    private static final Map<String, AccessTokenInfo> tokenCache = new ConcurrentHashMap<>();

    private static final Timer TOKEN_REFRESH_TIMER = new Timer();

    private WeChartConfigManager configManager;

    /**
     * 初始化数据
     */
    @Override
    public void init(WeChartConfigManager configManager) {
        this.configManager = configManager;
        Map<String, WeChartMpConfig> configs = configManager.getConfigs();
        configs.entrySet().parallelStream().forEach((item) -> {
            WeChartMpConfig itemConfig = item.getValue();
            AccessTokenOutputMessage tokenOutputMessage = new AccessTokenOutputMessage(itemConfig.getAppId(), itemConfig.getAppSecurity());
            AccessTokenInputMessage tokenInputMessage = WeChartMpClient.sendMessage(tokenOutputMessage);
            String errorCode = tokenInputMessage.getErrCode();
            if (errorCode == null || errorCode.equals("0")) {
                tokenCache.put(item.getKey(), new AccessTokenInfo(tokenInputMessage.getAccessToken()));
                long expireTime = (tokenInputMessage.getExpiresIn() - 120) * 1000;//微信Token失效时间 7200秒，提前2分钟刷新对用token，x1000转为毫秒
                TOKEN_REFRESH_TIMER.schedule(new TokenRefreshTimerTask(this, item.getKey()),
                        expireTime, expireTime);
                LOGGER.info("DefaultAccessTokenManager - refresh token schedule start - " + expireTime);
            } else {
                LOGGER.error("TOKEN获取异常：" + tokenInputMessage.getErrMsg());
            }
        });
    }

    /**
     * 重新获取Token
     *
     * @param name 配置的name值
     */
    @Override
    public void refreshToken(String name) {
        LOGGER.info("StartRefreshToken");
        WeChartMpConfig mpConfig = configManager.getConfig(name);
        if (mpConfig != null) {
            String token = getToken(mpConfig);
            AccessTokenInfo tokenInfo = tokenCache.get(name);
            if (tokenInfo == null) {
                tokenInfo = new AccessTokenInfo(token);
                tokenCache.putIfAbsent(name, tokenInfo);
            }
            tokenInfo.setToken(token);
            LOGGER.info("Token Refreshed");
        }
    }

    /**
     * 获取指定名字配置的Token信息
     *
     * @param name 配置的name值
     */
    @Override
    public String getToken(String name) {
        return tokenCache.get(name).getToken();
    }

    private String getToken(WeChartMpConfig mpConfig) {
        AccessTokenOutputMessage tokenOutputMessage = new AccessTokenOutputMessage(mpConfig.getAppId(), mpConfig.getAppSecurity());
        AccessTokenInputMessage tokenInputMessage = WeChartMpClient.sendMessage(tokenOutputMessage);
        return tokenInputMessage.getAccessToken();
    }
}
