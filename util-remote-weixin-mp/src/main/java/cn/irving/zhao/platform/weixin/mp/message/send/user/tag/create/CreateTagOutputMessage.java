package cn.irving.zhao.platform.weixin.mp.message.send.user.tag.create;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.user.tag.entity.TagInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@WeChartMessage(requestMethod = WeChartMessageRequestMethod.POST, requestType = WeChartMessageFormat.JSON)
public class CreateTagOutputMessage extends BaseMpSendOutputMessage<CreateTagInputMessage> {

    private static final String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=%s";

    private TagInfo tag;

    @Override
    public String getUrl() {
        return String.format(url, accessToken);
    }

    public void setTagName(String tagName) {
        if (tag == null) {
            tag = new TagInfo();
        }
        tag.setName(tagName);
    }

}
