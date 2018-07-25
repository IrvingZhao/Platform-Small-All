package cn.irving.zhao.platform.weixin.mp.message.send.web;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccessTokenOutputMessage extends BaseSendOutputMessage<UserAccessTokenInputMessage> {

    public UserAccessTokenOutputMessage(String appId, String appSecurity, String code) {
        this.appId = appId;
        this.appSecurity = appSecurity;
        this.code = code;
    }

    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    @JsonProperty("appid")
    private String appId;
    @JsonProperty("secret")
    private String appSecurity;
    private String code;
    @JsonProperty("grant_type")
    private String grantType = "authorization_code";

    @Override
    public String getUrl() {
        return REQUEST_URL;
    }

    @Override
    public Class<UserAccessTokenInputMessage> getInputMessageClass() {
        return UserAccessTokenInputMessage.class;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecurity() {
        return appSecurity;
    }

    public void setAppSecurity(String appSecurity) {
        this.appSecurity = appSecurity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
