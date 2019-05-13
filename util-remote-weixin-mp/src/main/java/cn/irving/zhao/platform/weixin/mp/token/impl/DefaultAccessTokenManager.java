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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * 默认token管理器
 */
public final class DefaultAccessTokenManager implements AccessTokenManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenManager.class);

    private static final Map<String, AccessTokenInfo> tokenCache = new ConcurrentHashMap<>();

    private static final Timer TOKEN_REFRESH_TIMER = new Timer();

    private Map<String, ReadWriteLock> lockMap = new ConcurrentHashMap<>();

    private WeChartConfigManager configManager;

    private Function<AccessTokenOutputMessage, AccessTokenInputMessage> tokenLoader;

    @Override
    public void init(WeChartConfigManager configManager, Function<AccessTokenOutputMessage, AccessTokenInputMessage> tokenLoader) {
        this.configManager = configManager;
        this.tokenLoader = tokenLoader;
    }

    @Override
    public void refreshToken(String name) {
        LOGGER.info("StartRefreshToken ==== {}", name);
        ReadWriteLock lock = lockMap.computeIfAbsent(name, k -> new ReentrantReadWriteLock());
        Lock writeLock = lock.writeLock();
        Lock readLock = lock.readLock();
        if (!writeLock.tryLock()) {
            LOGGER.info("Wait Refresh Token ==== {}", name);
            readLock.lock();//获取读锁
            LOGGER.info("Wait Finished ==== {}", name);
            return;
        }
        LOGGER.info("Exec refresh token ==== {}", name);
        AccessTokenInfo tokenInfo = tokenCache.computeIfAbsent(name, (k) -> new AccessTokenInfo(new TokenRefreshTimerTask(this, k)));//获取TokenInfo信息
        WeChartMpConfig config = configManager.getConfig(name);
        try {
            if (config == null) {
                LOGGER.info("Not Found Config, Clear Timers ==== {}", name);
                // 取消定时器，并移除tokenInfo
                tokenInfo.getRefreshTask().cancel();
                tokenCache.remove(name);
                throw new NullPointerException("Cannot find WeChartMpConfig by name [" + name + "]");//抛出未找到 配置异常
            } else {
                LOGGER.info("Send Token Refresh Request ==== {}", name);
                AccessTokenOutputMessage tokenOutputMessage = new AccessTokenOutputMessage(config.getAppId(), config.getAppSecurity());
                AccessTokenInputMessage tokenInputMessage = this.tokenLoader.apply(tokenOutputMessage);
                if (tokenInputMessage.getErrCode() != null && !"".equals(tokenInputMessage.getErrCode())) {
                    LOGGER.error("GET TOKEN FAIL：error message is [" + tokenInputMessage.getErrCode() + "]，reason is [" + tokenInputMessage.getErrMsg() + "]");
                    throw new RuntimeException("error message is [" + tokenInputMessage.getErrCode() + "]，reason is [" + tokenInputMessage.getErrMsg() + "]");//获取token异常
                }
                LOGGER.info("Set Token ==== {}", name);
                tokenInfo.setToken(tokenInputMessage.getAccessToken());
                if (tokenInfo.getRefreshTask().scheduledExecutionTime() == 0) {//下次执行时间为0，表示为开启定时器
                    LOGGER.info("Start RefreshTimer ==== {}", name);
                    long expireTime = (tokenInputMessage.getExpiresIn() - 120) * 1000;//微信Token失效时间 7200秒，提前2分钟刷新对用token，x1000转为毫秒
                    TOKEN_REFRESH_TIMER.schedule(tokenInfo.getRefreshTask(), expireTime, expireTime);
                }

            }
        } finally {
            LOGGER.info("RefreshToken Finish ==== {}", name);
            writeLock.unlock();
        }
    }

    @Override
    public String getToken(String name) {
        AccessTokenInfo tokenInfo = tokenCache.get(name);
        if (tokenInfo == null) {
            this.refreshToken(name);//刷新token，阻塞方法
            tokenInfo = tokenCache.get(name);//重新获取tokenInfo
        }
        return tokenInfo.getToken();
    }
}
