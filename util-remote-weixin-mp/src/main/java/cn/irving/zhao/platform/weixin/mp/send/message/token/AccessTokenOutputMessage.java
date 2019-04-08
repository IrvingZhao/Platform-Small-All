package cn.irving.zhao.platform.weixin.mp.send.message.token;

import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信 AccessToken 获取消息 - 请求消息
 *
 * @author Irving Zhao
 */
@WeChartMessage
@Setter
@Getter
public class AccessTokenOutputMessage extends BaseSendOutputMessage<AccessTokenInputMessage> {

    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/token";

    public AccessTokenOutputMessage(String appid, String secret) {
        this.appId = appid;
        this.secret = secret;
    }

    @JsonProperty("appid")
    private String appId;
    @JsonProperty("grant_type")
    private String grantType = "client_credential";
    private String secret;

    @Override
    public String getUrl() {
        return REQUEST_URL;
    }

}
