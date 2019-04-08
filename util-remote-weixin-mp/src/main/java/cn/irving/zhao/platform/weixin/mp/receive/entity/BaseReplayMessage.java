package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.SendMessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BaseReplayMessage {

    private final String toUserName;
    private final String fromUserName;
    private final Long createTime = System.currentTimeMillis();
    private final SendMessageType msgType;

}
