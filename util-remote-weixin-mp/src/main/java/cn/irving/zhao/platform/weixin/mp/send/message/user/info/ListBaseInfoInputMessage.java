package cn.irving.zhao.platform.weixin.mp.send.message.user.info;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListBaseInfoInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("user_info_list")
    private List<BaseInfo> userInfoList;

}
