package cn.irving.zhao.util.remote.mina.core;

import cn.irving.zhao.util.remote.mina.client.MinaClient;
import cn.irving.zhao.util.remote.mina.core.filter.*;
import cn.irving.zhao.util.remote.mina.core.handler.MinaMessageHandler;
import cn.irving.zhao.util.remote.mina.core.message.MinaMessage;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethodFactory;
import cn.irving.zhao.util.remote.mina.core.paired.PairedMessageLock;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;
import cn.irving.zhao.util.remote.mina.core.sign.ClientHashSaltHolder;
import cn.irving.zhao.util.remote.mina.core.sign.MinaMessageSignExecutor;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseMinaOperator {

    protected final Logger logger = LoggerFactory.getLogger(BaseMinaOperator.class);

    public static final String FILTER_NAME_CODEC = "filter_message_codec";//编码过滤器名称

    public static final String FILTER_NAME_SERIAL = "filter_message_serial";//序列化过滤器名称

    public static final String FILTER_NAME_MESSAGE_VALID_SIGN = "filter_message_valid_sign";//签名验证过滤器名称

    public static final String FILTER_NAME_MESSAGE_TIMEOUT_FILTER = "filter_message_valid_time";//消息过期检测过滤器名称

    public static final String FILTER_NAME_MESSAGE_REPEAT_FILTER = "filter_message_valid_repeat";//消息重复检测过滤器名称

    public static final String FILTER_NAME_MESSAGE_PAIRED_FILTER = "filter_message_paired";//成对消息响应过滤器名称

    public static final String METHOD_NAME_CLIENT_REGISTER = "method_client_register";//客户端注册方法名

    public static final String METHOD_NAME_MESSAGE_PAIRED_RESULT = "method_paired_message_result";//成对消息响应方法名

    /**
     * 执行方法工厂
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    protected MinaMessageMethodFactory methodFactory;// 方法执行器

    /**
     * 对象序列化执行器
     */
    protected MinaMessageSerialExecutor serialExecutor;

    /**
     * 签名 盐 管理器
     * <p>与 {@link BaseMinaOperator#signExecutor} 同时使用</p>
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    protected ClientHashSaltHolder saltHolder;

    /**
     * 签名执行器
     * <p>与 {@link BaseMinaOperator#saltHolder} 同时使用</p>
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    protected MinaMessageSignExecutor signExecutor;

    /**
     * 重复消息过滤，默认开启
     * <p>只有在 {@link BaseMinaOperator#enableMessageTimeValid} 有效时才会启用</p>
     */
    private Boolean enableMessageRepeatValid = true;

    /**
     * 消息过时过滤器，系统当前时间-消息发送时间<{@link #messageExpireTime} 时，消息才会执行
     * <p>只有在 {@link BaseMinaOperator#serialExecutor} 存在时才会启用</p>
     */
    private Boolean enableMessageTimeValid = true;

    /**
     * 消息过期时间
     * <p>只有在 {@link MinaClient#enableMessageTimeValid} 为true时才会有效</p>
     */
    private Long messageExpireTime = 2 * 60 * 1000L;

    /**
     * 消息执行器，为空时，则使用{@link MinaMessageHandler}
     * <p>{@link MinaMessageHandler} 只有在 {@link #serialExecutor} 不为空 时才会有效</p>
     */
    private IoHandler messageHandle;

    protected Charset charset = Charset.forName("UTF-8");//编码

    private Map<String, PairedMessageLock> messageLockMap = new ConcurrentHashMap<>();//pairedMessageLock

    protected void init() {
        logger.info("mina-base-start");
        DefaultIoFilterChainBuilder filterChainBuilder = new DefaultIoFilterChainBuilder();//过滤器组
        IoService ioService = getService();//具体执行类
        logger.info("mina-base-addCodeFilter");
        filterChainBuilder.addLast(FILTER_NAME_CODEC, new ProtocolCodecFilter(new TextLineCodecFactory(charset)));//编码过滤器
        if (serialExecutor != null) {
            logger.info("mina-base-addSerialFilter");
            filterChainBuilder.addLast(FILTER_NAME_SERIAL, new MinaMessageSerialFilter(serialExecutor));//序列化过滤器
            if (saltHolder != null && signExecutor != null) {//签名过滤器
                logger.info("mina-base-addSignFilter");
                filterChainBuilder.addLast(FILTER_NAME_MESSAGE_VALID_SIGN, new MinaMessageValidSignFilter(saltHolder, signExecutor));
            }
            if (enableMessageTimeValid) {//消息是否有效的过滤器
                logger.info("mina-base-addMessageTimeFilter");
                ioService.getFilterChain().addLast(FILTER_NAME_MESSAGE_TIMEOUT_FILTER, new MinaMessageValidTimeFilter(messageExpireTime));
                if (enableMessageRepeatValid) {//消息重复过滤
                    logger.info("mina-base-addMessageRepeatFilter");
                    ioService.getFilterChain().addLast(FILTER_NAME_MESSAGE_REPEAT_FILTER, new MinaMessageValidRepeatFilter(messageExpireTime));
                }
            }
            //成对消息响应拦截器
            filterChainBuilder.addLast(FILTER_NAME_MESSAGE_PAIRED_FILTER, new MinaMessagePairedResponseFilter(messageLockMap, serialExecutor));
            if (messageHandle == null) {//消息执行器
                if (methodFactory != null) {
                    logger.info("mina-base-setDefaultHandler");
                    ioService.setHandler(new MinaMessageHandler(methodFactory, serialExecutor));
                }
            }
        }
        if (messageHandle != null) {//自定义执行器不为空，则使用自定义消息执行器
            logger.info("mina-base-setCustomHandler");
            ioService.setHandler(messageHandle);
        }
        logger.info("mina-base-addCustomFilter");
        //在现有过滤器后添加原有过滤器
        ioService.getFilterChain().getAll().forEach(item -> {
            filterChainBuilder.addLast(item.getName(), item.getFilter());
        });
        //设置过滤器
        ioService.setFilterChainBuilder(filterChainBuilder);
        logger.info("mina-base-starting");
        start();//启动服务
        logger.info("mina-base-success");

    }

    /**
     * 获得服务器端/客户端具体实例
     */
    protected abstract IoService getService();

    /**
     * 启动服务
     */
    protected abstract void start();

    /**
     * 发送消息
     */
    protected abstract void sendMessage(MinaMessage minaMessage);

    /**
     * 发送成对消息
     */
    protected <T> T sendPairedMessage(MinaMessage minaMessage, Class<T> resultType) {
        PairedMessageLock<T> messageResult = getLock(minaMessage.getPairedId(), resultType);
        synchronized (messageResult) {
            this.sendMessage(minaMessage);
            try {
                messageResult.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (messageResult.getException() == null) {
                return messageResult.getResult();
            } else {
                throw messageResult.getException();
            }
        }
    }

    /**
     * 获得成对消息锁
     *
     * @param pairedId   消息对id
     * @param resultType 消息返回值类型
     */
    private <T> PairedMessageLock<T> getLock(String pairedId, Class<T> resultType) {
        PairedMessageLock<T> result;
        if ((result = messageLockMap.get(pairedId)) == null) {
            result = new PairedMessageLock<T>(resultType);
            messageLockMap.put(pairedId, result);
        }
        return result;
    }

    public MinaMessageMethodFactory getMethodFactory() {
        return methodFactory;
    }

    public void setMethodFactory(MinaMessageMethodFactory methodFactory) {
        this.methodFactory = methodFactory;
    }

    public MinaMessageSerialExecutor getSerialExecutor() {
        return serialExecutor;
    }

    public void setSerialExecutor(MinaMessageSerialExecutor serialExecutor) {
        this.serialExecutor = serialExecutor;
    }

    public ClientHashSaltHolder getSaltHolder() {
        return saltHolder;
    }

    public void setSaltHolder(ClientHashSaltHolder saltHolder) {
        this.saltHolder = saltHolder;
    }

    public MinaMessageSignExecutor getSignExecutor() {
        return signExecutor;
    }

    public void setSignExecutor(MinaMessageSignExecutor signExecutor) {
        this.signExecutor = signExecutor;
    }

    public Boolean getEnableMessageTimeValid() {
        return enableMessageTimeValid;
    }

    public void setEnableMessageTimeValid(Boolean enableMessageTimeValid) {
        this.enableMessageTimeValid = enableMessageTimeValid;
    }

    public Long getMessageExpireTime() {
        return messageExpireTime;
    }

    public void setMessageExpireTime(Long messageExpireTime) {
        this.messageExpireTime = messageExpireTime;
    }

    public IoHandler getMessageHandle() {
        return messageHandle;
    }

    public void setMessageHandle(IoHandler messageHandle) {
        this.messageHandle = messageHandle;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Map<String, PairedMessageLock> getMessageLockMap() {
        return messageLockMap;
    }

    public void setMessageLockMap(Map<String, PairedMessageLock> messageLockMap) {
        this.messageLockMap = messageLockMap;
    }

    public Boolean getEnableMessageRepeatValid() {
        return enableMessageRepeatValid;
    }

    public void setEnableMessageRepeatValid(Boolean enableMessageRepeatValid) {
        this.enableMessageRepeatValid = enableMessageRepeatValid;
    }

}
