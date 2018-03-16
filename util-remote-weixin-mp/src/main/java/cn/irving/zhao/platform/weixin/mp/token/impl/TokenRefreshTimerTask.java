package cn.irving.zhao.platform.weixin.mp.token.impl;

import cn.irving.zhao.platform.weixin.mp.token.AccessTokenManager;

import java.util.TimerTask;

/**
 * Token刷新定时任务
 *
 * @author Irving Zhao
 */
public class TokenRefreshTimerTask extends TimerTask {

    private AccessTokenManager tokenManager;
    private String key;

    public TokenRefreshTimerTask(AccessTokenManager tokenManager, String key) {
        this.tokenManager = tokenManager;
        this.key = key;
    }

    @Override
    public void run() {
        tokenManager.refreshToken(key);
    }
}
