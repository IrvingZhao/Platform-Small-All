package cn.irving.zhao.util.remote.mina.core.filter;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.server.timer.TimerUtil;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * mina消息过时过滤器
 */
public class MinaMessageValidTimeFilter extends IoFilterAdapter {

    private final Logger logger = LoggerFactory.getLogger(MinaMessageSerialFilter.class);

    public MinaMessageValidTimeFilter(Long messageFilterTime) {
        this.messageFilterTime = messageFilterTime;
    }

    private Long messageFilterTime;

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (MinaMessage.class.isInstance(message)) {
            MinaMessage minaMessage = (MinaMessage) message;
            if (System.currentTimeMillis() - minaMessage.getSendDate() < messageFilterTime) {// 消息超时
                logger.info("mina-messageTimeValid-success-{}" + minaMessage.getMessageId());
                nextFilter.messageReceived(session, message);
            }
        }
    }
}
