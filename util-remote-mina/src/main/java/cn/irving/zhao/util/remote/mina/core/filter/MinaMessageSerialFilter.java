package cn.irving.zhao.util.remote.mina.core.filter;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mina消息序列化过滤器
 */
public class MinaMessageSerialFilter extends IoFilterAdapter {

    public MinaMessageSerialFilter(MinaMessageSerialExecutor serialExecutor) {
        this.serialExecutor = serialExecutor;
    }

    private final Logger logger = LoggerFactory.getLogger(MinaMessageSerialFilter.class);
    private MinaMessageSerialExecutor serialExecutor;

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        Object requestMessage = writeRequest.getMessage();
        if (MinaMessage.class.isInstance(requestMessage)) {//如果是指定消息类型，则进行数据格式化后传输
            String data = serialExecutor.serial(requestMessage);
            logger.info("mina-send-success-{}", data);
            nextFilter.filterWrite(session, new DefaultWriteRequest(data));
        }
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        MinaMessage minaMessage = serialExecutor.parse(String.valueOf(message), MinaMessage.class);
        logger.info("mina-receive-success-{}", message);
        nextFilter.messageReceived(session, minaMessage);
    }
}
