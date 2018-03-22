package cn.irving.zhao.platform.weixin.mp.message.send.material.temporary;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;

/**
 * @author zhaojn1
 * @version AddTempMaterialOutputMessage.java, v 0.1 2018/3/19 zhaojn1
 * @project userProfile
 */
@WeChartMessage(requestType = WeChartMessageFormat.MULTIPART, requestMethod = WeChartMessageRequestMethod.POST)
public class AddTempMaterialOutputMessage extends BaseMpSendOutputMessage<AddTempMaterialInputMessage> {

    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

    @JsonIgnore
    private String type;

    private File media;

    public AddTempMaterialOutputMessage(String token) {
        super(token);
    }

    @Override
    public String getUrl() {
        return String.format(REQUEST_URL, accessToken, type);
    }

    @Override
    public Class<AddTempMaterialInputMessage> getInputMessageClass() {
        return AddTempMaterialInputMessage.class;
    }

    public String getType() {
        return type;
    }

    public AddTempMaterialOutputMessage setType(String type) {
        this.type = type;
        return this;
    }

    public File getMedia() {
        return media;
    }

    public AddTempMaterialOutputMessage setMedia(File media) {
        this.media = media;
        return this;
    }
}
