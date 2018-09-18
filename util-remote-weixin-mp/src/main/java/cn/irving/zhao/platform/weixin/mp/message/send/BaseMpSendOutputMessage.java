package cn.irving.zhao.platform.weixin.mp.message.send;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 公众号-发送消息-输出消息 基础类
 */
@Setter
@Getter
public abstract class BaseMpSendOutputMessage<T extends BaseMpSendInputMessage> extends BaseSendOutputMessage<T> {

    @JsonIgnore
    protected String accessToken;
}
