package cn.irving.zhao.platform.weixin.mp.message.send;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 公众号-发送消息-输出消息 基础类
 */
public abstract class BaseMpSendOutputMessage<T extends BaseMpSendInputMessage> extends BaseSendOutputMessage<T> {

    @JsonIgnore
    protected String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
