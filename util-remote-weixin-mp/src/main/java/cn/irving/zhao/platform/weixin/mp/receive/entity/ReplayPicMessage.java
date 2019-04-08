package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.SendMessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ReplayPicMessage extends BaseReplayMessage {
    public ReplayPicMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName, SendMessageType.IMAGE);
    }

    @JsonProperty("Image")
    @JsonIgnoreProperties({"title", "description"})
    private ReplayMedia image;

    /**
     * 设置图片信息
     *
     * @param mediaId 图片媒体id
     */
    @JsonIgnore
    public ReplayPicMessage setImage(String mediaId) {
        this.image = new ReplayMedia(mediaId);
        return this;
    }
}
