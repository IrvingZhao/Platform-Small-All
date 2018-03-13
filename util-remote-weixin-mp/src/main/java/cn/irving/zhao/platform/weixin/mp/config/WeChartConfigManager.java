package cn.irving.zhao.platform.weixin.mp.config;

import java.util.Map;

/**
 * 微信配置管理器
 */
public interface WeChartConfigManager {

    /**
     * 管理器初始化
     */
    void init();

    /**
     * 初始化/重新加载 微信配置信息
     */
    void loadWeChartConfig();

    /**
     * 获得指定name值的微信配置信息
     *
     * @param name name值
     * @return 微信配置信息
     */
    WeChartMpConfig getConfig(String name);

    /**
     * 获得所有微信账户配置
     *
     * @return 所有微信配置信息
     */
    Map<String, WeChartMpConfig> getConfigs();

}
