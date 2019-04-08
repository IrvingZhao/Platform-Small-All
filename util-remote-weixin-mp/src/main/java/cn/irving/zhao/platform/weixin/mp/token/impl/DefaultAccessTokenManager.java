package cn.irving.zhao.platform.weixin.mp.token.impl;


import cn.irving.zhao.platform.weixin.mp.config.WeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.config.WeChartMpConfig;
import cn.irving.zhao.platform.weixin.mp.send.message.token.AccessTokenInputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.token.AccessTokenOutputMessage;
import cn.irving.zhao.platform.weixin.mp.token.AccessTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * 默认token管理器
 */
public final class DefaultAccessTokenManager implements AccessTokenManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenManager.class);

    private static final Map<String, AccessTokenInfo> tokenCache = new ConcurrentHashMap<>();

    private static final Timer TOKEN_REFRESH_TIMER = new Timer();

    private Map<String, ReentrantLock> lockMap = new HashMap<>();

    private WeChartConfigManager configManager;

    private Function<AccessTokenOutputMessage, AccessTokenInputMessage> tokenLoader;

    /**
     * 初始化数据
     */
    @Override
    public void init(WeChartConfigManager configManager, Function<AccessTokenOutputMessage, AccessTokenInputMessage> tokenLoader) {
        this.configManager = configManager;
        this.tokenLoader = tokenLoader;

        Map<String, WeChartMpConfig> configs = configManager.getConfigs();
        configs.entrySet().parallelStream().forEach((item) -> {
            lockMap.put(item.getKey(), new ReentrantLock());
            WeChartMpConfig itemConfig = item.getValue();
            AccessTokenOutputMessage tokenOutputMessage = new AccessTokenOutputMessage(itemConfig.getAppId(), itemConfig.getAppSecurity());
            AccessTokenInputMessage tokenInputMessage = tokenLoader.apply(tokenOutputMessage);
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
        ReentrantLock lock = lockMap.get(name);
        AccessTokenInfo tokenInfo = tokenCache.get(name);
        if (lock.isLocked()) {
            return;
        }
        //多线程锁+读写锁
        lock.lock();
        if (tokenInfo == null) {
            tokenInfo = new AccessTokenInfo("");
        }
        tokenInfo.lockWrite();
        try {
            WeChartMpConfig mpConfig = configManager.getConfig(name);
            if (mpConfig != null) {
                String token = getToken(mpConfig);
                tokenCache.putIfAbsent(name, tokenInfo);
                tokenInfo.setToken(token);
                LOGGER.info("Token Refreshed - NewToken：" + token.substring(0, 5) + "*******");
            } else {
                throw new NullPointerException("Cannot find WeChartMpConfig by name [" + name + "]");
            }
        } finally {
            lock.unlock();
            tokenInfo.unlockWrite();
        }
    }

    /**
     * 获取指定名字配置的Token信息
     *
     * @param name 配置的name值
     */
    @Override
    public String getToken(String name) {
        AccessTokenInfo tokenInfo = tokenCache.get(name);
        if (tokenInfo == null) {
            refreshToken(name);
            return getToken(name);
        }
        return tokenInfo.getToken();
    }

    private String getToken(WeChartMpConfig mpConfig) {
        AccessTokenOutputMessage tokenOutputMessage = new AccessTokenOutputMessage(mpConfig.getAppId(), mpConfig.getAppSecurity());
        AccessTokenInputMessage tokenInputMessage = this.tokenLoader.apply(tokenOutputMessage);
        if (tokenInputMessage.getErrCode() != null && !"".equals(tokenInputMessage.getErrCode())) {
            LOGGER.error("GET TOKEN FAIL：error message is [" + tokenInputMessage.getErrCode() + "]，reason is [" + tokenInputMessage.getErrMsg() + "]");
        }
        return tokenInputMessage.getAccessToken();
    }
}
