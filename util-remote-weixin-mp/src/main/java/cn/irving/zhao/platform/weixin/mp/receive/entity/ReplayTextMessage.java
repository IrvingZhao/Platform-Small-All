package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.SendMessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplayTextMessage extends BaseReplayMessage {
    public ReplayTextMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName, SendMessageType.TEXT);
    }

    @JsonProperty("Content")
    private String content;

}
