package cn.irving.zhao.platform.weixin.mp.send.message.menu.search;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.menu.entity.Menu;
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
