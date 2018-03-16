package cn.irving.zhao.platform.weixin.mp.message.menu.conditional.create;

import cn.irving.zhao.platform.weixin.mp.message.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 个性化菜单创建输入消息
 */
public class ConditionalMenuCreateInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("menuid")
    private String menuId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
