package cn.irving.zhao.platform.weixin.mp.send.message.user.list;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenIdListOutputMessage extends BaseMpSendOutputMessage<OpenIdListInputMessage> {

    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s";

    @JsonProperty("next_openid")
    private String nextOpenId;

    @Override
    public String getUrl() {
        return String.format(REQUEST_URL, super.accessToken);
    }
}
