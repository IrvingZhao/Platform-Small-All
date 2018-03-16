package cn.irving.zhao.platform.weixin.mp.message.menu.search;

import cn.irving.zhao.platform.weixin.mp.message.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.menu.entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 菜单查询-输入消息
 */
public class SearchMenuInputMessage extends BaseMpSendInputMessage {

    private Menu menu;//普通菜单

    @JsonProperty("conditionalmenu")
    private List<Menu> conditionalMenu;//个性化菜单

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getConditionalMenu() {
        return conditionalMenu;
    }

    public void setConditionalMenu(List<Menu> conditionalMenu) {
        this.conditionalMenu = conditionalMenu;
    }
}
