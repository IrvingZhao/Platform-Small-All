package cn.irving.zhao.platform.weixin.mp.message.send.material.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NewsMaterial {

    @JsonProperty("media_id")
    private String mediaId;

    private List<NewsItem> content;

    @JsonProperty("update_time")
    private Date updateTime;

}
