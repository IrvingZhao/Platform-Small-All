package cn.irving.zhao.platform.weixin.mp.send.message.menu.conditional.delete;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 删除个性化菜单
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionalMenuDeleteOutputMessage extends BaseMpSendOutputMessage<ConditionalMenuDeleteInputMessage> {

    @JsonProperty("menuid")
    private String menuId;

    @Override
    public String getUrl() {
        return String.format("https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=%s", accessToken);
    }

}
