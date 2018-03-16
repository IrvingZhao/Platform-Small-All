package cn.irving.zhao.platform.weixin.mp.message.menu.search;

import cn.irving.zhao.platform.weixin.mp.message.BaseMpSendOutputMessage;

/**
 * 查询菜单-输出消息
 */
public class SearchMenuOutputMessage extends BaseMpSendOutputMessage<SearchMenuInputMessage> {

    public SearchMenuOutputMessage(String token) {
        super(token);
    }

    @Override
    public Class<SearchMenuInputMessage> getInputMessageClass() {
        return SearchMenuInputMessage.class;
    }

    @Override
    public String getUrl() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";
        return String.format(url, accessToken);
    }
}
