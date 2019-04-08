package cn.irving.zhao.platform.weixin.mp.send.message.menu.create;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.menu.entity.Button;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建菜单输出消息
 */
@Getter
@Setter
@WeChartMessage(requestMethod = WeChartMessageRequestMethod.POST, requestType = WeChartMessageFormat.JSON)
public class CreateMenuOutputMessage extends BaseMpSendOutputMessage<CreateMenuInputMessage> {

    private List<Button> button;

    public CreateMenuOutputMessage addButton(Button button) {
        if (this.button == null) {
            synchronized (this) {
                if (this.button == null) {
                    this.button = new ArrayList<>();
                }
            }
        }
        this.button.add(button);
        return this;
    }

    @Override
    public String getUrl() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
        return String.format(url, accessToken);
    }

}
