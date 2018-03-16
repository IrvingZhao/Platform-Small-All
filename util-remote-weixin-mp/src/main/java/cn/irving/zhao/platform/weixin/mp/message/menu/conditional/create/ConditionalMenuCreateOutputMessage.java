package cn.irving.zhao.platform.weixin.mp.message.menu.conditional.create;

import cn.irving.zhao.platform.weixin.mp.message.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.message.menu.entity.Button;
import cn.irving.zhao.platform.weixin.mp.message.menu.entity.MatchRule;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 个性化菜单创建请求消息
 */
public class ConditionalMenuCreateOutputMessage extends BaseMpSendOutputMessage<ConditionalMenuCreateInputMessage> {

    @JsonProperty("button")
    private List<Button> buttons = new ArrayList<>();

    @JsonProperty("matchrule")
    private MatchRule matchRule;

    protected ConditionalMenuCreateOutputMessage(String token) {
        super(token);
    }

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
    public Class<ConditionalMenuCreateInputMessage> getInputMessageClass() {
        return ConditionalMenuCreateInputMessage.class;
    }

    @Override
    public String getUrl() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=%s";
        return String.format(url, accessToken);
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public MatchRule getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(MatchRule matchRule) {
        this.matchRule = matchRule;
    }
}
