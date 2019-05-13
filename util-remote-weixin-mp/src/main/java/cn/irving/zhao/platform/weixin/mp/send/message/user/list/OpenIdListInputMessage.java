package cn.irving.zhao.platform.weixin.mp.send.message.user.list;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.user.tag.entity.OpenIds;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenIdListInputMessage extends BaseMpSendInputMessage {

    private int total;

    private int count;

    private OpenIds data;

    @JsonProperty("next_openid")
    private String nextOpenId;


}
