package cn.irving.zhao.util.remote.mina.core.message;

/**
 * 消息数据封装
 */
public class MinaMessageDataWrapper implements MinaMessageData {
    public MinaMessageDataWrapper(String clientId, String method, Object data) {
        this.clientId = clientId;
        this.method = method;
        this.data = data;
    }

    private final String clientId;
    private final String method;
    private final Object data;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public Object getData() {
        return data;
    }
}