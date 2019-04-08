package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.SendMessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ReplayMusicMessage extends BaseReplayMessage {
    public ReplayMusicMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName, SendMessageType.MUSIC);
    }

    @JsonProperty("Music")
    private ReplayMusic music;

    /**
     * 设置音乐参数
     *
     * @param title        音乐标题
     * @param description  音乐描述
     * @param musicUrl     音乐文件链接
     * @param HQMusicUrl   高音质音乐文件链接
     * @param thumbMediaId 缩略图 媒体id，不能为空
     */
    @JsonIgnore
    public ReplayMusicMessage setMusic(String title, String description, String musicUrl, String HQMusicUrl, String thumbMediaId) {
        this.music = new ReplayMusic(title, description, musicUrl, HQMusicUrl, thumbMediaId);
        return this;
    }

}
