package cn.irving.zhao.platform.weixin.mp.token.impl;


import cn.irving.zhao.platform.weixin.mp.config.WeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.token.AccessTokenManager;

/**
 * 默认token管理器
 */
public class DefaultAccessTokenManager implements AccessTokenManager {

    /**
     * 初始化数据
     */
    @Override
    public void init(WeChartConfigManager configManager) {
        //TODO 初始化配置，缓存Token内容
    }

    /**
     * 重新获取Token
     *
     * @param name 配置的name值
     */
    @Override
    public void refreshToken(String name) {
        //TODO 根据配置信息，重新获取Token
    }

    /**
     * 获取指定名字配置的Token信息
     *
     * @param name 配置的name值
     */
    @Override
    public String getToken(String name) {
        return null;
    }

    /**
     * 设置指定name的token
     *
     * @param name  配置的name值
     * @param token token值
     */
    @Override
    public void setToken(String name, String token) {

    }
}
