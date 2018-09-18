package cn.irving.zhao.platform.weixin.mp.message.send.menu.delete;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;

/**
 * 删除全部自定义菜单，包括个性化菜单
 */
public class DeleteMenuOutputMessage extends BaseMpSendOutputMessage<DeleteMenuInputMessage> {

    @Override
    public String getUrl() {
        return String.format("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s", accessToken);
    }
}
