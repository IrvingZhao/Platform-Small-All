package cn.irving.zhao.platform.weixin.mp.send.message;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
