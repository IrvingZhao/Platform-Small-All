package cn.irving.zhao.platform.weixin.mp.token;


import cn.irving.zhao.platform.weixin.mp.config.WeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.message.send.token.AccessTokenInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.token.AccessTokenOutputMessage;

import java.util.function.Function;

/**
 * Token管理器接口
 */
public interface AccessTokenManager {

    /**
     * 初始化token值
     *
     * @param configManager 微信账户配置信息
     */
    void init(WeChartConfigManager configManager, Function<AccessTokenOutputMessage, AccessTokenInputMessage> tokenLoader);

    /**
     * 刷新指定name值的token
     *
     * @param name 配置的name值
     */
    void refreshToken(String name);

    /**
     * 获得指定name值的token
     *
     * @param name 配置的name值
     * @return token
     */
    String getToken(String name);

}
