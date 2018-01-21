package cn.irving.zhao.util.remote.mina.server.session;

import cn.irving.zhao.util.remote.mina.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.server.timer.TimerUtil;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * mina 客户端验证过滤器
 */
public class MinaServerClientFilter extends IoFilterAdapter {

    public MinaServerClientFilter(MinaClientHolder sessionHolder, Long clearTime) {
        this.clientHolder = sessionHolder;
        sessionCleanTaskMap = new HashMap<>();
        sessionClientIdMapper = new HashMap<>();
        this.clearTime = clearTime;
    }

    private MinaClientHolder clientHolder;

    private Map<Long, String> sessionClientIdMapper;// session id 和 客户端之间的关联映射

    private Map<Long, TimerTask> sessionCleanTaskMap;// 未认证session 清理任务

    private Long clearTime;//清理时间

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
        TimerTask task = new SessionCleanTask(session);
        sessionCleanTaskMap.put(session.getId(), task);// 开启 会话超时认证
        TimerUtil.schedule(task, clearTime);
        nextFilter.sessionCreated(session);
    }

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
        clientHolder.delSession(sessionClientIdMapper.get(session.getId()));
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (MinaMessage.class.isInstance(message)) {
            MinaMessage minaMessage = (MinaMessage) message;
            if (BaseMinaOperator.METHOD_NAME_CLIENT_REGISTER.equals(minaMessage.getMethod())) {
                TimerTask task = sessionCleanTaskMap.get(session.getId());
                boolean result = true;
                if (task != null) {
                    result = task.cancel();//停止 清理会话 任务
                }
                if (result) {//清理成功，执行客户端注册
                    MinaClientModel client = new MinaClientModel(session);
                    client.setClientId(minaMessage.getClientId());
                    client.setSign(true);
                    clientHolder.addClient(minaMessage.getClientId(), client);
                }
            } else {
                MinaClientModel client = clientHolder.getClient(minaMessage.getClientId());//获得客户端
                if (client != null && client.getSign()) { //检查客户端是否存在以及客户端是否认证
                    nextFilter.messageReceived(session, message);
                }
            }
        }
    }

    private final class SessionCleanTask extends TimerTask {

        private IoSession session;

        private SessionCleanTask(IoSession session) {
            this.session = session;
        }

        @Override
        public void run() {
            sessionCleanTaskMap.remove(session.getId());
            session.closeNow().awaitUninterruptibly();//立即关闭
        }
    }

    public MinaClientHolder getClientHolder() {
        return clientHolder;
    }

    public MinaServerClientFilter setClientHolder(MinaClientHolder clientHolder) {
        this.clientHolder = clientHolder;
        return this;
    }

    public Long getClearTime() {
        return clearTime;
    }

    public MinaServerClientFilter setClearTime(Long clearTime) {
        this.clearTime = clearTime;
        return this;
    }
}
