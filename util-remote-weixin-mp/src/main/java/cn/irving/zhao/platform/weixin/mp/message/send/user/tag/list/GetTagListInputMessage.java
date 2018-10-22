package cn.irving.zhao.platform.weixin.mp.message.send.user.tag.list;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.user.tag.entity.TagInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTagListInputMessage extends BaseMpSendInputMessage {
    private List<TagInfo> tags;
}
