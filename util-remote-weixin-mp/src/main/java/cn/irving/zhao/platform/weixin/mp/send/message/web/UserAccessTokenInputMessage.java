package cn.irving.zhao.platform.weixin.mp.send.message.web;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户AccessToken 响应类
 */
@Setter
@Getter
public class UserAccessTokenInputMessage extends BaseSendInputMessage {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("scope")
    private String scope;

}
