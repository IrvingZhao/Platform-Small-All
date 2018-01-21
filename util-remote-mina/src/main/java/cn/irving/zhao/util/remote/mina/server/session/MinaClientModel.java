package cn.irving.zhao.util.remote.mina.server.session;

import cn.irving.zhao.util.remote.mina.core.message.MinaMessageData;
import org.apache.mina.core.session.IoSession;

/**
 * 会话保存器
 */
public class MinaClientModel {

    public MinaClientModel(IoSession session) {
        this.session = session;
        this.sign = false;
    }

    private String clientId;

    private IoSession session;

    private Boolean sign;

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    public void sendMessage(Object message) {
        session.write(message);
    }

    String getClientId() {
        return clientId;
    }

    MinaClientModel setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    IoSession getSession() {
        return session;
    }

    MinaClientModel setSession(IoSession session) {
        this.session = session;
        return this;
    }

    Boolean getSign() {
        return sign;
    }

    MinaClientModel setSign(Boolean sign) {
        this.sign = sign;
        return this;
    }
}
