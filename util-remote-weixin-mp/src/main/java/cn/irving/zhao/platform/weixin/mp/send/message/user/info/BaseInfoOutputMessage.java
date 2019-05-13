package cn.irving.zhao.platform.weixin.mp.send.message.user.info;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseInfoOutputMessage extends BaseMpSendOutputMessage<BaseInfoInputMessage> {

    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s";

    @JsonProperty("openid")
    private String openId;
    private String lang = "zh_CN";

    @Override
    public String getUrl() {
        return String.format(REQUEST_URL, super.accessToken);
    }
}
