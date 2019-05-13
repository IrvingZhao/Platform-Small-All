package cn.irving.zhao.platform.weixin.mp.send.message.user.info;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@WeChartMessage(requestMethod = WeChartMessageRequestMethod.POST, requestType = WeChartMessageFormat.JSON)
public class ListBaseInfoOutputMessage extends BaseMpSendOutputMessage<ListBaseInfoInputMessage> {

    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=%s";

    @Override
    public String getUrl() {
        return String.format(REQUEST_URL, super.accessToken);
    }

    @JsonProperty("user_list")
    private List<UserItem> userList = new ArrayList<>();

    @JsonIgnore
    public ListBaseInfoOutputMessage addUserItem(String openId) {
        this.userList.add(new UserItem(openId));
        return this;
    }

}
