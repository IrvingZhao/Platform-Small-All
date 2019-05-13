package cn.irving.zhao.platform.weixin.mp.send.message.material.permanent.count;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialCountInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("voice_count")
    private int voiceCount;
    @JsonProperty("video_count")
    private int videoCount;
    @JsonProperty("image_count")
    private int imageCount;
    @JsonProperty("news_count")
    private int newsCount;

}
