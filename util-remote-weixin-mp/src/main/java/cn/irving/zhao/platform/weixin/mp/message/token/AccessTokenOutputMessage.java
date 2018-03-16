package cn.irving.zhao.platform.weixin.mp.message.token;

import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信 AccessToken 获取消息 - 请求消息
 *
 * @author Irving Zhao
 */
@WeChartMessage
public class AccessTokenOutputMessage extends BaseSendOutputMessage<AccessTokenInputMessage> {

    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/token";

    public AccessTokenOutputMessage(String appid, String secret) {
        this.appId = appid;
        this.secret = secret;
    }

    @JsonProperty("appid")
    private String appId;
    @JsonProperty("grant_type")
    private String grantType = "client_credential";
    private String secret;

    @Override
    public String getUrl() {
        return REQUEST_URL;
    }

    @Override
    public Class<AccessTokenInputMessage> getInputMessageClass() {
        return AccessTokenInputMessage.class;
    }

    public String getAppId() {
        return appId;
    }

    public AccessTokenOutputMessage setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public AccessTokenOutputMessage setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public AccessTokenOutputMessage setSecret(String secret) {
        this.secret = secret;
        return this;
    }
}
