package cn.irving.zhao.platform.weixin.mp.config;

import cn.irving.zhao.platform.weixin.mp.config.enums.WeChartMessageSecurityType;

/**
 * 微信配置实体类
 *
 * @author Irving.Zhao
 */
public final class WeChartMpConfig {

    private final String appId;
    private final String appSecurity;
    private final String securityToken;
    private final String encodingAesKey;
    private final WeChartMessageSecurityType messageType;

    /**
     * 初始化微信配置
     *
     * @param appId          微信配置项：appId
     * @param appSecurity    微信配置项：appSecurity
     * @param securityToken  微信配置项：securityToken
     * @param encodingAesKey 微信配置项：encodingAesKey
     * @param messageType    微信配置项：消息加密类型
     */
    public WeChartMpConfig(String appId, String appSecurity, String securityToken, String encodingAesKey, WeChartMessageSecurityType messageType) {
        this.appId = appId;
        this.appSecurity = appSecurity;
        this.securityToken = securityToken;
        this.encodingAesKey = encodingAesKey;
        this.messageType = messageType;
    }

    /**
     * 初始化微信配置
     *
     * @param appId          微信配置项：appId
     * @param appSecurity    微信配置项：appSecurity
     * @param securityToken  微信配置项：securityToken
     * @param encodingAesKey 微信配置项：encodingAesKey
     * @param messageType    微信配置项：消息加密类型
     */
    public WeChartMpConfig(String appId, String appSecurity, String securityToken, String encodingAesKey, String messageType) {
        this.appId = appId;
        this.appSecurity = appSecurity;
        this.securityToken = securityToken;
        this.encodingAesKey = encodingAesKey;
        this.messageType = WeChartMessageSecurityType.valueOf(messageType.toUpperCase());
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecurity() {
        return appSecurity;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public WeChartMessageSecurityType getMessageType() {
        return messageType;
    }

}
