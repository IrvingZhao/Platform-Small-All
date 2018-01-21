package cn.irving.zhao.util.remote.mina.server.session;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端保存器
 */
public class MinaClientHolder {

    private transient final Map<String, MinaClientModel> clientMap = new HashMap<>();

    public MinaClientModel getClient(String clientKey) {
        return clientMap.get(clientKey);
    }

    public void addClient(String clientKey, MinaClientModel client) {
        clientMap.put(clientKey, client);
    }

    public void delSession(String sessionKey) {
        clientMap.remove(sessionKey);
    }

}

