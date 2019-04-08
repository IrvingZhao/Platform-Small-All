package cn.irving.zhao.platform.weixin.mp.send.message.user.tag.list;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;

public class GetTagListOutputMessage extends BaseMpSendOutputMessage<GetTagListInputMessage> {

    private static final String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=%s";

    @Override
    public String getUrl() {
        return String.format(url, accessToken);
    }
}
