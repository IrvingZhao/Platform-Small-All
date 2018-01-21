package cn.irving.zhao.util.remote.mina.client;

import cn.irving.zhao.util.remote.mina.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

/**
 * mina 客户端使用类
 */
public class MinaClient extends BaseMinaOperator {

    public MinaClient() {
    }

    /**
     * @param host 连接地址
     * @param port 连接端口
     */
    public MinaClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 连接地址
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 客户端id
     */

    private String clientId;

    /**
     * 客户端签名盐
     */
    private String salt;

    /**
     * 添加的额外过滤器
     */
    private LinkedHashMap<String, IoFilter> filters;

    /**
     * 开启认证模式
     * <p>只有在{@link MinaClient#salt}或{@link BaseMinaOperator#saltHolder} 、 {@link BaseMinaOperator#signExecutor}不为空时启用</p>
     */
    private Boolean enableSign = true;

    /**
     * 是否启动自动客户端注册，如果服务器开启客户端认证，建议开启
     */
    private Boolean autoRegisterClient = false;

    private SocketConnector connector;

    private IoSession session;

    @Override
    public void init() {
        logger.info("mina-client-init");
        if (connector == null) {
            logger.info("mina-client-createConnector");
            connector = new NioSocketConnector();
        }
        if (filters != null && !filters.isEmpty()) {
            logger.info("mina-client-addCustomFilter");
            filters.forEach(this.connector.getFilterChain()::addLast);
        }
        if (salt != null && !salt.trim().isEmpty() && enableSign) {
            logger.info("mina-client-setSaltHolder");
            super.saltHolder = key -> salt;
        }

        super.init();

        logger.info("mina-client-initiated");

    }

    @Override
    protected IoService getService() {
        return connector;
    }

    @Override
    protected void start() {
        try {
            logger.info("mina-client-bind-{}-{}", host, port);
            ConnectFuture future = connector.connect(new InetSocketAddress(InetAddress.getByName(host), port));
            future.awaitUninterruptibly();
            this.session = future.getSession();
            if (autoRegisterClient) {
                this.sendMessage(METHOD_NAME_CLIENT_REGISTER, "");//注册客户端
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param method 调用方法
     * @param data   传输数据
     */
    public void sendMessage(String method, Object data) {
        String jsonData = serialExecutor.serial(data);
        this.sendMessage(MinaMessage.createMinaMessage(clientId, method, jsonData));
    }

    /**
     * 发送成对消息
     *
     * @param method     远程执行方法
     * @param data       发送数据
     * @param resultType 接收数据类型
     */
    public <T> T sendPairedMessage(String method, Object data, Class<T> resultType) {
        String jsonData = serialExecutor.serial(data);
        return this.sendPairedMessage(MinaMessage.createPairedMinaMessage(clientId, method, jsonData), resultType);
    }

    /**
     * 发送消息
     *
     * @param minaMessage 消息对象
     */
    protected void sendMessage(MinaMessage minaMessage) {
        minaMessage.setSendDate(System.currentTimeMillis());
        session.write(minaMessage);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LinkedHashMap<String, IoFilter> getFilters() {
        return filters;
    }

    public void setFilters(LinkedHashMap<String, IoFilter> filters) {
        this.filters = filters;
    }

    public Boolean getEnableSign() {
        return enableSign;
    }

    public void setEnableSign(Boolean enableSign) {
        this.enableSign = enableSign;
    }

    public Boolean getAutoRegisterClient() {
        return autoRegisterClient;
    }

    public void setAutoRegisterClient(Boolean autoRegisterClient) {
        this.autoRegisterClient = autoRegisterClient;
    }
}
