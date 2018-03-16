package cn.irving.zhao.platform.weixin.mp.message.menu.delete;

import cn.irving.zhao.platform.weixin.mp.message.BaseMpSendOutputMessage;

/**
 * 删除全部自定义菜单，包括个性化菜单
 */
public class DeleteMenuOutputMessage extends BaseMpSendOutputMessage<DeleteMenuInputMessage> {

    protected DeleteMenuOutputMessage(String token) {
        super(token);
    }

    @Override
    public Class<DeleteMenuInputMessage> getInputMessageClass() {
        return DeleteMenuInputMessage.class;
    }

    @Override
    public String getUrl() {
        return String.format("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s", accessToken);
    }
}
