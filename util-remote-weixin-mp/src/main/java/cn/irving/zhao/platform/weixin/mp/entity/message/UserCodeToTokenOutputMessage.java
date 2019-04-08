package cn.irving.zhao.platform.weixin.mp.entity.message;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCodeToTokenOutputMessage extends BaseSendOutputMessage<UserCodeToTokenInputMessage> {
    private final static String URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    @JsonProperty("appid")
    private String appId;

    @JsonProperty("secret")
    private String secret;

    private String code;

    @JsonProperty("grant_type")
    private final String grantType = "authorization_code";

    @Override
    public String getUrl() {
        return URL;
    }
}
