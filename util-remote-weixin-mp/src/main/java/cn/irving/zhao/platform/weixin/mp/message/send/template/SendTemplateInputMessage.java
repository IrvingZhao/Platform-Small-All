package cn.irving.zhao.platform.weixin.mp.message.send.template;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendTemplateInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("msgid")
    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
