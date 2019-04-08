package cn.irving.zhao.platform.weixin.mp.send.message.menu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 个性化菜单匹配规则类
 */
@Getter
@Setter
public class MatchRule {

    @JsonProperty("tag_id")
    private String tagId;

    private Sex sex;

    @JsonProperty("client_platform_type")
    private PlatformType platform;

    private String country;

    private String province;

    private String city;

    private String language;

}
