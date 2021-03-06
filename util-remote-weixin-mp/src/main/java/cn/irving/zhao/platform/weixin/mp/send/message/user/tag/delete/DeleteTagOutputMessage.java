package cn.irving.zhao.platform.weixin.mp.send.message.user.tag.delete;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.user.tag.entity.TagInfo;
import lombok.Getter;
import lombok.Setter;

@WeChartMessage(requestMethod = WeChartMessageRequestMethod.POST, requestType = WeChartMessageFormat.JSON)
@Getter
@Setter
public class DeleteTagOutputMessage extends BaseMpSendOutputMessage<DeleteTagInputMessage> {

    private static final String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=%s";

    private TagInfo tag;

    @Override
    public String getUrl() {
        return String.format(url, accessToken);
    }
}
