package cn.irving.zhao.platform.weixin.mp.send.message.material.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * */
@Getter
@Setter
public class NewsMaterial {

    @JsonProperty("media_id")
    private String mediaId;

    private NewsListContent content;

    @JsonProperty("update_time")
    private Date updateTime;

}
