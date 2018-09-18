package cn.irving.zhao.platform.weixin.mp.message.send.menu.search;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.menu.entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 菜单查询-输入消息
 */
@Getter
@Setter
public class SearchMenuInputMessage extends BaseMpSendInputMessage {

    private Menu menu;//普通菜单

    @JsonProperty("conditionalmenu")
    private List<Menu> conditionalMenu;//个性化菜单

}
