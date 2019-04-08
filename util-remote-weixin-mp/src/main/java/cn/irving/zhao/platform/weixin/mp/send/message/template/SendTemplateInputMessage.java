package cn.irving.zhao.platform.weixin.mp.send.message.template;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 模板消息响应类
 * */
@Setter
@Getter
public class SendTemplateInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("msgid")
    private String msgId;

}
