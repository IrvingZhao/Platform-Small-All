package cn.irving.zhao.platform.weixin.mp.config.impl;


import cn.irving.zhao.platform.weixin.mp.config.WeChartConfigManager;
import cn.irving.zhao.platform.weixin.mp.config.WeChartMpConfig;
import cn.irving.zhao.util.base.property.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 默认加载，读取Properties配置信息
 * name为配置key，读取配置时使用，其他项对应微信开发配置中的各项内容。
 * 配置内容：
 * <ul>
 * <li>wx.&lt;name&gt;.appId</li>
 * <li>wx.&lt;name&gt;.appSecurity</li>
 * <li>wx.&lt;name&gt;.securityToken</li>
 * <li>wx.&lt;name&gt;.encodingAesKey</li>
 * <li>wx.&lt;name&gt;.messageType</li>
 * </ul>
 */
public class DefaultWeChartConfigManager implements WeChartConfigManager {

    private Pattern configPattern = Pattern.compile("wx\\.(.*)\\.(.*)");

    private Map<String, WeChartMpConfig> configMap = new HashMap<>();

    /**
     * 管理器初始化
     */
    @Override
    public void init() {
        loadWeChartConfig();
    }

    /**
     * 初始化/重新加载 微信配置信息
     */
    @Override
    public void loadWeChartConfig() {
        configMap.clear();
        Map<String, Map<String, String>> configCache = new HashMap<>();
        Property.getKeyValues(configPattern).forEach((key, value) -> {
            Matcher matcher = configPattern.matcher(key);
            Map<String, String> itemConfig = configCache.computeIfAbsent(matcher.group(1), k -> new HashMap<String, String>());
            itemConfig.put(matcher.group(2), value);
        });
        configCache.forEach((key, value) -> {
            WeChartMpConfig config = new WeChartMpConfig(value.get("appId"),
                    value.get("appSecurity"),
                    value.get("securityToken"),
                    value.get("encodingAesKey"),
                    value.get("messageType"));
            configMap.put(key, config);
        });
    }

    /**
     * 获得指定name值的微信配置信息
     *
     * @param name name值
     */
    @Override
    public WeChartMpConfig getConfig(String name) {
        return configMap.get(name);
    }

    /**
     * 获得所有微信账户配置
     */
    @Override
    public Map<String, WeChartMpConfig> getConfigs() {
        return configMap;
    }
}
