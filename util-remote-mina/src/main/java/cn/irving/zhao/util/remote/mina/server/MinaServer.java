package cn.irving.zhao.util.remote.mina.server;

import cn.irving.zhao.util.remote.mina.core.BaseMinaOperator;
import cn.irving.zhao.util.remote.mina.core.exception.MinaUtilException;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.server.session.MinaClientHolder;
import cn.irving.zhao.util.remote.mina.server.session.MinaClientModel;
import cn.irving.zhao.util.remote.mina.server.session.MinaServerClientFilter;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoService;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;

public class MinaServer extends BaseMinaOperator {

    public static final String FILTER_NAME_CLIENT_VALID = "filter_client_valid";//客户端认证filter

    public MinaServer() {
    }

    public MinaServer(Integer port) {
        this.port = port;
    }

    public MinaServer(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 绑定的host
     */
    private String host;

    /**
     * 监听的端口
     */
    private Integer port;

    /**
     * 添加的额外过滤器
     */
    private LinkedHashMap<String, IoFilter> filters;

    /**
     * 会话保存器
     */
    private MinaClientHolder clientHolder;

    /**
     * 开启客户端认证模式，默认开启
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    private Boolean enableClientValid = true;

    /**
     * 会话在客户端未验证时保存的时间，单位 毫秒，默认为30秒
     * <p>只有在{@link MinaServer#enableClientValid} 为true时才会有效</p>
     */
    private Long clientExpireTime = 30 * 1000L;

    private IoAcceptor service;

    @Override
    public void init() {
        logger.info("mina-server-init");
        if (this.service == null) { //service 未指定，则创建默认
            logger.info("mina-server-createAcceptor");
            this.service = new NioSocketAcceptor();
        }
        if (clientHolder == null) {//客户端保存器如果未指定，创建默认
            logger.info("mina-server-createClientHolder");
            clientHolder = new MinaClientHolder();
        }
        if (filters != null && !filters.isEmpty()) {//filter如果存在，循环添加
            logger.info("mina-server-addCustomFilter");
            filters.forEach(this.service.getFilterChain()::addLast);
        }
        if (super.serialExecutor != null) {//如果定义了序列化执行器，则可选择是否添加客户端认证
            if (enableClientValid) {
                logger.info("mina-server-addClientValid");
                this.service.getFilterChain().addLast(FILTER_NAME_CLIENT_VALID, new MinaServerClientFilter(clientHolder, clientExpireTime));
            }
        }
        logger.info("mina-server-parentInit");
        super.init();
        logger.info("mina-server-initiated");
    }

    @Override
    protected IoService getService() {
        return service;
    }

    @Override
    protected void start() {
        try {
            logger.info("mina-server-start");
            InetAddress address = null;
            if (host != null && !host.trim().isEmpty()) {
                logger.info("mina-server-bindHost-{}", host);
                address = InetAddress.getByName(host);
            }
            logger.info("mina-server-bindPort-{}", port);
            service.bind(new InetSocketAddress(address, port));
        } catch (IOException e) {
            logger.error("mina-server-startError", e);
            throw new MinaUtilException("mina server 启动异常", e);
        }

    }

    /**
     * 发送消息
     *
     * @param clientId 接收消息的客户端的id，或对应的sessionId
     * @param method   客户端执行的方法
     * @param data     数据
     */
    public void sendMessage(String clientId, String method, Object data) {
        String jsonData = serialExecutor.serial(data);
        this.sendMessage(MinaMessage.createMinaMessage(clientId, method, jsonData));
    }

    /**
     * 发送成对消息
     *
     * @param clientId   目标客户端id
     * @param method     消息接收方执行方法
     * @param data       数据对象
     * @param resultType 返回值类型
     */
    public <T> T sendPairedMessage(String clientId, String method, Object data, Class<T> resultType) {
        String jsonData = serialExecutor.serial(data);//TODO 去除所有位置对 serialExecutor的依赖
        return this.sendPairedMessage(MinaMessage.createPairedMinaMessage(clientId, method, jsonData), resultType);
    }

    /**
     * 发送消息
     *
     * @param minaMessage 消息对象
     */
    protected void sendMessage(MinaMessage minaMessage) {
        MinaClientModel client = clientHolder.getClient(minaMessage.getClientId());
        minaMessage.setSendDate(System.currentTimeMillis());
        client.sendMessage(minaMessage);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public LinkedHashMap<String, IoFilter> getFilters() {
        return filters;
    }

    public void setFilters(LinkedHashMap<String, IoFilter> filters) {
        this.filters = filters;
    }

    public MinaClientHolder getClientHolder() {
        return clientHolder;
    }

    public void setClientHolder(MinaClientHolder clientHolder) {
        this.clientHolder = clientHolder;
    }

    public Boolean getEnableClientValid() {
        return enableClientValid;
    }

    public void setEnableClientValid(Boolean enableClientValid) {
        this.enableClientValid = enableClientValid;
    }

    public Long getClientExpireTime() {
        return clientExpireTime;
    }

    public void setClientExpireTime(Long clientExpireTime) {
        this.clientExpireTime = clientExpireTime;
    }
}
