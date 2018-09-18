package cn.irving.zhao.platform.weixin.mp.message.send.token;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信 AccessToken 获取消息 - 响应消息
 *
 * @author Irving Zhao
 */
@Setter
@Getter
public class AccessTokenInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private long expiresIn;
}
