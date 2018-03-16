package cn.irving.zhao.platform.weixin.mp.token.impl;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhaojn1
 * @version AccessTokenInfo.java, v 0.1 2018/3/15 zhaojn1
 * @project userProfile
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

    public void setToken(String token) {
        try {
            lock.writeLock().lock();
            this.token = token;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
