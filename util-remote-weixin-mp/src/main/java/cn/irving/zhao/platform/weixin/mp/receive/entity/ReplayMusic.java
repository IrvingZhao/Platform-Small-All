package cn.irving.zhao.platform.weixin.mp.receive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplayMusic {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("MusicURL")
    private String musicUrl;

    @JsonProperty("HQMusicUrl")
    private String HQMusicUrl;

    @JsonProperty("ThumbMediaId")
    private String thumbMediaId;

}
