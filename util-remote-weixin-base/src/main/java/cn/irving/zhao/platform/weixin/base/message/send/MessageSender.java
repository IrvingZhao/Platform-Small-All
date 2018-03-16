package cn.irving.zhao.platform.weixin.base.message.send;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessageConfig;
import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import cn.irving.zhao.util.remote.http.HttpClient;
import cn.irving.zhao.util.remote.http.enums.HttpMethod;
import cn.irving.zhao.util.remote.http.enums.RequestType;
import cn.irving.zhao.util.remote.http.message.HttpMessage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * 消息发送器
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public class MessageSender {
    private static final ObjectStringSerialUtil serialUtil = ObjectStringSerialUtil.getSerialUtil();
    private HttpClient httpUtil = new HttpClient();

    /**
     * 发送微信请求
     */
    public <T extends BaseSendInputMessage> T sendMessage(BaseSendOutputMessage<T> outputMessage) {
        WeChartMessageConfig messageConfig = outputMessage.getMessageConfig();
        BaseHttpMessage httpMessage = new BaseHttpMessage(outputMessage);

        httpUtil.sendMessage(httpMessage);
        ObjectStringSerialUtil.SerialType serialType = ObjectStringSerialUtil.SerialType.JSON;
        switch (messageConfig.getResponseType()) {
            case JSON:
                serialType = ObjectStringSerialUtil.SerialType.JSON;
                break;
            case XML:
                serialType = ObjectStringSerialUtil.SerialType.XML;
                break;
        }
        Class<T> inputClass = outputMessage.getInputMessageClass();
        return serialUtil.parse(httpMessage.responseStream, inputClass, serialType);
//      TODO 请求结果非200情况下的异常信息
    }

    private static class BaseHttpMessage implements HttpMessage {
        private String url;
        private HttpMethod httpMethod = HttpMethod.GET;
        private RequestType requestType = RequestType.NORMAL;
        private Map<String, Object> requestParams;
        private InputStream requestStream;
        private int resultCode;
        private InputStream responseStream;


        BaseHttpMessage(BaseSendOutputMessage outputMessage) {
            WeChartMessageConfig messageConfig = outputMessage.getMessageConfig();
            this.url = outputMessage.getUrl();
            switch (messageConfig.getRequestMethod()) {
                case POST:
                    this.httpMethod = HttpMethod.POST;
                case GET:
                    this.httpMethod = HttpMethod.GET;
            }
            WeChartMessageFormat requestFormat = messageConfig.getRequestType();
            switch (requestFormat) {
                case FORM:
                    this.requestParams = outputMessage.getParamMap();
                    break;
                case XML:
                    this.requestStream = new ByteArrayInputStream(serialUtil.serial(outputMessage, ObjectStringSerialUtil.SerialType.XML).getBytes());
                    break;
                case JSON:
                    this.requestStream = new ByteArrayInputStream(serialUtil.serial(outputMessage, ObjectStringSerialUtil.SerialType.JSON).getBytes());
                    break;
            }
        }

        @Override
        public String getRequestUrl() {
            return url;
        }

        @Override
        public HttpMethod getRequestMethod() {
            return httpMethod;
        }

        @Override
        public RequestType getRequestType() {
            return requestType;
        }

        @Override
        public Map<String, Object> getRequestParams() {
            return requestParams;
        }

        @Override
        public InputStream getRequestStream() {
            return requestStream;
        }

        @Override
        public void setResponseCode(int code) {
            this.resultCode = code;
        }

        @Override
        public void setResponseHead(Map<String, String> head) {
        }

        @Override
        public void setResponseStream(InputStream inputStream) {
            this.responseStream = inputStream;
        }
    }

}