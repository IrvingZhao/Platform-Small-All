package cn.irving.zhao.platform.weixin.mp.send.message.user.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserItem {

    @JsonProperty("openid")
    private final String openId;

    private String lang = "zh_CN";

}
