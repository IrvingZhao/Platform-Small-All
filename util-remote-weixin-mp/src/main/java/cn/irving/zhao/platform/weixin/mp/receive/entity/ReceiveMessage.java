package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.MessageEventType;
import cn.irving.zhao.platform.weixin.mp.config.enums.ReceiverMessageType;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ReceiveMessage {

    @JsonProperty("ToUserName")
    private String toUserName;

    @JsonProperty("FromUserName")
    private String fromUserName;

    @JsonProperty("CreateTime")
    private Long createTime;

    @JsonProperty("MsgType")
    private ReceiverMessageType msgType;

    @JsonProperty("MsgId")
    private String msgId;

    @JsonProperty("Event")
    private MessageEventType event;

    @JsonIgnore
    private Map<String, Object> otherData = new HashMap<>();

    @JsonAnySetter
    public void set(String key, Object value) {
        otherData.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return this.otherData;
    }

    @JsonIgnore
    public Object getOtherData(String key) {
        return otherData.get(key);
    }
}
