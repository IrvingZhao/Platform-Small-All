package cn.irving.zhao.platform.weixin.mp.receive;

import cn.irving.zhao.platform.weixin.mp.receive.entity.BaseReplayMessage;
import cn.irving.zhao.platform.weixin.mp.receive.entity.ReceiveMessage;

/**
 * 消息执行器
 */
public interface MessageExecutor {

    /**
     * 执行消息接收
     *
     * @param configName     微信配置key
     * @param receiveMessage 接收的消息
     * @return 返回的消息，返回结果为null时，表示返回空消息
     */
    BaseReplayMessage execute(String configName, ReceiveMessage receiveMessage);

}
