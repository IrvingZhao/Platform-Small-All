package cn.irving.zhao.util.remote.mina.core.filter;

import cn.irving.zhao.util.remote.mina.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina.core.exception.MinaUtilException;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.paired.PairedMessageLock;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Map;

/**
 * 成对消息接收响应的过滤器
 */
public class MinaMessagePairedResponseFilter extends IoFilterAdapter {

    public MinaMessagePairedResponseFilter(Map<String, PairedMessageLock> messageLockMap, MinaMessageSerialExecutor serialExecutor) {
        this.messageLockMap = messageLockMap;
        this.serialExecutor = serialExecutor;
    }

    private Map<String, PairedMessageLock> messageLockMap;//锁保存map

    private MinaMessageSerialExecutor serialExecutor;//序列化执行器，用于转换响应消息

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {

        if (MinaMessage.class.isInstance(message)) {//检测消息类型
            MinaMessage minaMessage = (MinaMessage) message;
            if (BaseMinaOperator.METHOD_NAME_MESSAGE_PAIRED_RESULT.equals(minaMessage.getMethod())) {//如果消息的方法是成对消息响应时，执行相关操作
                PairedMessageLock<Object> messageLock = messageLockMap.remove(minaMessage.getPairedId());//在map中，移除锁，避免多次调用
                if (messageLock != null) {//检查锁是否为空
                    synchronized (messageLock) {//同步执行
                        if (minaMessage.getErrorId() != null) {//检查返回值是否有异常
                            messageLock.setException(new MinaUtilException("服务器出现异常，异常id：" + minaMessage.getErrorId() + "，异常信息：" + minaMessage.getErrorMessage()));
                        } else {
                            Object result = serialExecutor.parse(minaMessage.getData(), messageLock.getResultType());
                            messageLock.setResult(result);
                        }
                        messageLock.notifyAll();//通知所有等待线程启动
                        return;
                    }
                }
            }
        }
        nextFilter.messageReceived(session, message);
    }


}
