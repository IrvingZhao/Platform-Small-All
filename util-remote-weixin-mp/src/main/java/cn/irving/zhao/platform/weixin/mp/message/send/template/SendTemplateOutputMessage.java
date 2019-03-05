package cn.irving.zhao.platform.weixin.mp.message.send.template;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息发送类
 * */
@WeChartMessage(requestMethod = WeChartMessageRequestMethod.POST,
        requestType = WeChartMessageFormat.JSON)
@Setter
@Getter
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

    /**
     * 配置重定向小程序
     * @param appId 小程序 appid
     * @param path 跳转地址
     */
    public void setMiniProgramConfig(String appId, String path) {
        this.miniProgramConfig = new MiniProgramConfig(appId, path);
    }

    @Override
    public String getUrl() {
        return String.format(requestUrl, accessToken);
    }

}
