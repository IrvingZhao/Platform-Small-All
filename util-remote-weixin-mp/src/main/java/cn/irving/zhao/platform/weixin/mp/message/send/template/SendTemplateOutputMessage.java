package cn.irving.zhao.platform.weixin.mp.message.send.template;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@WeChartMessage(requestMethod = WeChartMessageRequestMethod.POST,
        requestType = WeChartMessageFormat.JSON)
public class SendTemplateOutputMessage extends BaseMpSendOutputMessage<SendTemplateInputMessage> {

    private static final String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    @JsonProperty("touser")
    private String toUser;
    @JsonProperty("template_id")
    private String templateId;
    @JsonProperty("url")
    private String redirectUrl;
    @JsonProperty("miniprogram")
    private MiniProgramConfig miniProgramConfig;
    private Map<String, Map<String, String>> data;

    public SendTemplateOutputMessage addData(String key, String content) {
        return this.addData(key, content, null);
    }

    public SendTemplateOutputMessage addData(String key, String content, String color) {
        if (data == null) {
            data = new HashMap<>();
        }
        Map<String, String> itemData = new HashMap<>();
        itemData.put("value", content);
        if (color != null) {
            itemData.put("color", color);
        }
        data.put(key, itemData);
        return this;
    }

    @Override
    public String getUrl() {
        return String.format(requestUrl, accessToken);
    }

    @Override
    public Class<SendTemplateInputMessage> getInputMessageClass() {
        return SendTemplateInputMessage.class;
    }

    public static String getRequestUrl() {
        return requestUrl;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public MiniProgramConfig getMiniProgramConfig() {
        return miniProgramConfig;
    }

    public void setMiniProgramConfig(MiniProgramConfig miniProgramConfig) {
        this.miniProgramConfig = miniProgramConfig;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
