package cn.irving.zhao.platform.weixin.mp.send.message.web;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户AccessToken 请求类
 * */
@Setter
@Getter
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

}
