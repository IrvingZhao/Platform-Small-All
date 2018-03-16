package cn.irving.zhao.platform.weixin.mp.token.impl;

import cn.irving.zhao.platform.weixin.mp.token.AccessTokenManager;

import java.util.TimerTask;

/**
 * @author zhaojn1
 * @version TokenRefreshTimerTask.java, v 0.1 2018/3/15 zhaojn1
 * @project userProfile
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
