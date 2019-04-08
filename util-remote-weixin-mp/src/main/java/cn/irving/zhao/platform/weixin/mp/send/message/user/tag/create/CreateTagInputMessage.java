package cn.irving.zhao.platform.weixin.mp.send.message.user.tag.create;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.user.tag.entity.TagInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTagInputMessage extends BaseMpSendInputMessage {

    private TagInfo tag;

}
