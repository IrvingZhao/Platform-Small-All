package cn.irving.zhao.platform.weixin.mp.send.message.material.list;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListMaterialInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("item_count")
    private int itemCount;

}
