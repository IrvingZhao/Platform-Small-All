package cn.irving.zhao.platform.weixin.mp.message.send.user.tag.create;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.user.tag.entity.TagInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTagInputMessage extends BaseMpSendInputMessage {

    private TagInfo tag;

}
