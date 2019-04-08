package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.SendMessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ReplayVoiceMessage extends BaseReplayMessage {
    public ReplayVoiceMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName, SendMessageType.VOICE);
    }

    @JsonProperty("Voice")
    @JsonIgnoreProperties({"title", "description"})
    private ReplayMedia voice;

    /**
     * 设置录音信息
     *
     * @param mediaId 媒体id
     */
    @JsonIgnore
    public ReplayVoiceMessage setVoice(String mediaId) {
        this.voice = new ReplayMedia(mediaId);
        return this;
    }
}
