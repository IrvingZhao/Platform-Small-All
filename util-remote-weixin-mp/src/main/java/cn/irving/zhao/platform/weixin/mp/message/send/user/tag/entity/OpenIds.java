package cn.irving.zhao.platform.weixin.mp.message.send.user.tag.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OpenIds {

    @JsonProperty("openid")
    private List<String> openId;

    public void addOpenId(String openId) {
        if (this.openId == null) {
            this.openId = new ArrayList<>();
        }
        this.openId.add(openId);
    }

}
