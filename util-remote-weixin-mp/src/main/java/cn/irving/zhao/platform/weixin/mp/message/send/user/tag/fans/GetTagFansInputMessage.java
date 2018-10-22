package cn.irving.zhao.platform.weixin.mp.message.send.user.tag.fans;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.user.tag.entity.OpenIds;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTagFansInputMessage extends BaseMpSendInputMessage {
    private Integer count;

    private OpenIds data;

    @JsonProperty("next_openid")
    private String nextOpenId;
}
