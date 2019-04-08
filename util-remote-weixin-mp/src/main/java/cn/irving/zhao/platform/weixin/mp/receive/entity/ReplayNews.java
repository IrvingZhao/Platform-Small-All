package cn.irving.zhao.platform.weixin.mp.receive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplayNews {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("PicUrl")
    private String picUrl;
    @JsonProperty("Url")
    private String url;

}
