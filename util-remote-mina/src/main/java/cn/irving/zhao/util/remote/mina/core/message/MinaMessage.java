package cn.irving.zhao.util.remote.mina.core.message;

import cn.irving.zhao.util.remote.mina.core.BaseMinaOperator;
import com.fasterxml.jackson.annotation.*;

import java.util.UUID;

/**
 * mina 交互消息
 */
public final class MinaMessage {

    @JsonCreator
    private MinaMessage(@JsonProperty("messageId") String messageId) {
        this.messageId = messageId;
    }

    private MinaMessage(String clientId, String method, String data) {
        this.messageId = UUID.randomUUID().toString();
        this.clientId = clientId;
        this.method = method;
        this.data = data;
    }

    private final String messageId;

    private String clientId;

    private String method;//执行方法

    private String data;//交互数据

    private String sign;//签名

    private Long sendDate;//发送时间

    private String pairedId;//消息对id

    private Long errorId;//异常id

    private String errorMessage;//异常信息

    /**
     * 构建mina消息
     *
     * @param clientId 客户端id
     * @param method   执行方法
     * @param data     消息数据
     */
    public static MinaMessage createMinaMessage(String clientId, String method, String data) {
        return new MinaMessage(clientId, method, data);
    }

    public static MinaMessage createPairedMinaMessage(String clientId, String method, String data) {
        MinaMessage result = new MinaMessage(clientId, method, data);
        result.pairedId = UUID.randomUUID().toString();
        return result;
    }

    public MinaMessage generateResultMessage(String data) {
        MinaMessage result = new MinaMessage(this.clientId, BaseMinaOperator.METHOD_NAME_MESSAGE_PAIRED_RESULT, data);
        result.pairedId = this.pairedId;
        return result;
    }

    public MinaMessage generateErrorMessage(Long errorId, String message) {
        MinaMessage result = new MinaMessage(this.clientId, BaseMinaOperator.METHOD_NAME_MESSAGE_PAIRED_RESULT, data);
        result.pairedId = this.pairedId;
        result.errorId = errorId;
        result.errorMessage = message;
        return result;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    public String getPairedId() {
        return pairedId;
    }

    public void setPairedId(String pairedId) {
        this.pairedId = pairedId;
    }

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
