package cn.irving.zhao.platform.weixin.mp.message.menu.conditional.delete;

import cn.irving.zhao.platform.weixin.mp.message.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 删除个性化菜单
 */
public class ConditionalMenuDeleteOutputMessage extends BaseMpSendOutputMessage<ConditionalMenuDeleteInputMessage> {

    @JsonProperty("menuid")
    private String menuId;


    protected ConditionalMenuDeleteOutputMessage(String token) {
        super(token);
    }

    @Override
    public Class<ConditionalMenuDeleteInputMessage> getInputMessageClass() {
        return ConditionalMenuDeleteInputMessage.class;
    }

    @Override
    public String getUrl() {
        return String.format("https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=%s", accessToken);
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
