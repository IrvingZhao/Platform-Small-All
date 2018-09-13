package cn.irving.zhao.platform.weixin.mp.token.impl;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Token 信息存放类
 *
 * @author Irving Zhao
 */
public class AccessTokenInfo {

    public AccessTokenInfo(String token) {
        this.token = token;
    }

    private String token;

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
