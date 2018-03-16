package cn.irving.zhao.platform.weixin.mp.message.token;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhaojn1
 * @version AccessTokenInputMessage.java, v 0.1 2018/3/14 zhaojn1
 * @project userProfile
 */
public class AccessTokenInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public AccessTokenInputMessage setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public AccessTokenInputMessage setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
