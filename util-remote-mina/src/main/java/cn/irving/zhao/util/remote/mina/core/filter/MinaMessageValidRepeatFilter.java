package cn.irving.zhao.util.remote.mina.core.filter;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.server.timer.TimerUtil;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * mina消息过滤器，超时或重复消息自动过滤
 */
public class MinaMessageValidRepeatFilter extends IoFilterAdapter {

    private final Logger logger = LoggerFactory.getLogger(MinaMessageSerialFilter.class);

    public MinaMessageValidRepeatFilter(Long messageFilterTime) {
        TimerUtil.schedule(() -> {//清理messageId映射
            waitCleanMessageIds.clear();
            waitCleanMessageIds.putAll(messageIdCache);
            messageIdCache.clear();
        }, messageFilterTime << 1);//清理时间为消息过期时间的2倍，避免误清理
    }

    private Map<String, Boolean> messageIdCache = new HashMap<>();

    private Map<String, Boolean> waitCleanMessageIds = new HashMap<>();

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (MinaMessage.class.isInstance(message)) {
            MinaMessage minaMessage = (MinaMessage) message;
            if (messageIdCache.get(minaMessage.getMessageId()) == null && waitCleanMessageIds.get(minaMessage.getMessageId()) == null) {//消息是否重复发送
                logger.info("mina-messageValid-success-{}" + minaMessage.getMessageId());
                nextFilter.messageReceived(session, message);
            }
        }
    }
}
