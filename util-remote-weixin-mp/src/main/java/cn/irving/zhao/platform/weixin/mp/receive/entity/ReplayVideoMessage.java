package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.SendMessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ReplayVideoMessage extends BaseReplayMessage {
    public ReplayVideoMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName, SendMessageType.VIDEO);
    }

    @JsonProperty("Video")
    private ReplayMedia video;

    /**
     * 设置视频信息
     *
     * @param mediaId     视频媒体id
     * @param title       视频标题
     * @param description 视频描述
     */
    @JsonIgnore
    public ReplayVideoMessage setVideo(String mediaId, String title, String description) {
        this.video = new ReplayMedia(mediaId, title, description);
        return this;
    }
}
