package cn.irving.zhao.platform.weixin.mp.message;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 公众号-发送消息-输出消息 基础类
 */
public abstract class BaseMpSendOutputMessage<T extends BaseMpSendInputMessage> extends BaseSendOutputMessage<T> {

    @JsonProperty("access_token")
    protected String accessToken;

    protected BaseMpSendOutputMessage(String token) {
        this.accessToken = token;
    }

}
