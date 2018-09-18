package cn.irving.zhao.platform.weixin.mp.message.send.menu.conditional.create;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.menu.entity.Button;
import cn.irving.zhao.platform.weixin.mp.message.send.menu.entity.MatchRule;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 个性化菜单创建请求消息
 */
@Getter
@Setter
public class ConditionalMenuCreateOutputMessage extends BaseMpSendOutputMessage<ConditionalMenuCreateInputMessage> {

    @JsonProperty("button")
    private List<Button> buttons = new ArrayList<>();

    @JsonProperty("matchrule")
    private MatchRule matchRule;

    public ConditionalMenuCreateOutputMessage addButton(Button button) {
        if (this.buttons == null) {
            synchronized (this) {
                if (this.buttons == null) {
                    this.buttons = new ArrayList<>();
                }
            }
        }
        this.buttons.add(button);
        return this;
    }

    @Override
    public String getUrl() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=%s";
        return String.format(url, accessToken);
    }

}
