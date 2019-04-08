package cn.irving.zhao.platform.weixin.mp.send.message;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 公众号-发送消息-输入消息 基础类
 */
@Setter
@Getter
public abstract class BaseMpSendInputMessage extends BaseSendInputMessage {

    @JsonProperty("errcode")
    private String errCode;

    @JsonProperty("errmsg")
    private String errMsg;

}
