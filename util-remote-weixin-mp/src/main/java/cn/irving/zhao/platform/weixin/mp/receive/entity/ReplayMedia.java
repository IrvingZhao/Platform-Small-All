package cn.irving.zhao.platform.weixin.mp.receive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ReplayMedia {

    @JsonProperty("MediaId")
    private final String mediaId;


    /**
     * 仅在Video时使用
     */
    @JsonProperty("Title")
    private String title;

    /**
     * 仅在Video时使用
     */
    @JsonProperty("Description")
    private String description;
}
