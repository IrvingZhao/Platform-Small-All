package cn.irving.zhao.util.remote.mina.core.filter;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.sign.ClientHashSaltHolder;
import cn.irving.zhao.util.remote.mina.core.sign.MinaMessageSignExecutor;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaMessageValidSignFilter extends IoFilterAdapter {

    private final Logger logger = LoggerFactory.getLogger(MinaMessageValidSignFilter.class);

    public MinaMessageValidSignFilter(ClientHashSaltHolder saltHolder, MinaMessageSignExecutor signExecutor) {
        this.saltHolder = saltHolder;
        this.signExecutor = signExecutor;
    }

    private ClientHashSaltHolder saltHolder;

    private MinaMessageSignExecutor signExecutor;

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        Object message = writeRequest.getMessage();
        if (MinaMessage.class.isInstance(message)) {// 如果是 mina message，写入sign
            MinaMessage minaMessage = (MinaMessage) message;
            String salt = saltHolder.getSalt(minaMessage.getClientId());
            minaMessage.setSign(signExecutor.getMessageSign(minaMessage.getData(), salt));
            logger.info("mina-setSign-success-{}", minaMessage.getMessageId());
            super.filterWrite(nextFilter, session, writeRequest);
        }
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (MinaMessage.class.isInstance(message)) {// 如果是 mina message，校验sign
            MinaMessage minaMessage = (MinaMessage) message;
            String salt = saltHolder.getSalt(minaMessage.getClientId());
            if (signExecutor.validMessage(minaMessage.getData(), minaMessage.getSign(), salt)) {//校验通过，执行下一个过滤器
                logger.info("mina-checkSign-success-{}", minaMessage.getMessageId());
                nextFilter.messageReceived(session, message);
            } else {
                logger.info("mina-checkSign-error-{}", minaMessage.getMessageId());
            }
        }
    }

}
