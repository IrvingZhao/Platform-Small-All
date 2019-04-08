package cn.irving.zhao.platform.weixin.mp.send.message.user.userTag.set;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.user.tag.entity.OpenIds;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@WeChartMessage(requestMethod = WeChartMessageRequestMethod.POST, requestType = WeChartMessageFormat.JSON)
public class SetUserTagOutputMessage extends BaseMpSendOutputMessage<SetUserTagInputMessage> {

    private static final String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=%s";

    @JsonProperty("openid_list")
    private OpenIds userOpenIds;

    @JsonProperty("tagid")
    private Integer tagId;

    @Override
    public String getUrl() {
        return String.format(url, accessToken);
    }

    public SetUserTagOutputMessage addUserOpenId(String openId) {
        if (userOpenIds == null) {
            userOpenIds = new OpenIds();
        }
        userOpenIds.addOpenId(openId);
        return this;
    }
}
