package cn.irving.zhao.platform.weixin.mp.send.message.user.tag.list;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.user.tag.entity.TagInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTagListInputMessage extends BaseMpSendInputMessage {
    private List<TagInfo> tags;
}
