package cn.irving.zhao.platform.weixin.mp.send.message.user.tag.fans;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@WeChartMessage(requestType = WeChartMessageFormat.JSON, requestMethod = WeChartMessageRequestMethod.POST)
public class GetTagFansOutputMessage extends BaseMpSendOutputMessage<GetTagFansInputMessage> {

    private static final String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=%s";

    @JsonProperty("tagid")
    private Integer tagId;

    @JsonProperty("next_openid")
    private String nextOpenId;

    @Override
    public String getUrl() {
        return String.format(url, accessToken);
    }
}
