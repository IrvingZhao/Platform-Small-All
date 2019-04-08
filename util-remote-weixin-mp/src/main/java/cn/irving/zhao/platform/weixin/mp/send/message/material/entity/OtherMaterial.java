package cn.irving.zhao.platform.weixin.mp.send.message.material.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class OtherMaterial {

    @JsonProperty("media_id")
    private String mediaId;
    private String name;
    @JsonProperty("update_time")
    private Date updateTime;
    private String url;

}
