package cn.irving.zhao.platform.weixin.mp.token.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Token 信息存放类
 *
 * @author Irving Zhao
 */
@RequiredArgsConstructor
public class AccessTokenInfo {

    public AccessTokenInfo(String token, TokenRefreshTimerTask refreshTask) {
        this.token = token;
        this.refreshTask = refreshTask;
    }

    private String token;

    @Getter
    private final TokenRefreshTimerTask refreshTask;

    private ReadWriteLock lock = new ReentrantReadWriteLock(false);

    public String getToken() {
        try {
            lock.readLock().lock();
            return token;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void lockWrite() {
        lock.writeLock().lock();
    }

    public void unlockWrite() {
        lock.writeLock().unlock();
    }

    public void setToken(String token) {
        this.token = token;
    }
}
