package cn.irving.zhao.platform.weixin.mp.message.send.menu.conditional.create;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 个性化菜单创建输入消息
 */
@Getter
@Setter
public class ConditionalMenuCreateInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("menuid")
    private String menuId;

}
