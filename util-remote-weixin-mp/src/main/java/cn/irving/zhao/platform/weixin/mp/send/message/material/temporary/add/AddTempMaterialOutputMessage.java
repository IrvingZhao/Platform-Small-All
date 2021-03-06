package cn.irving.zhao.platform.weixin.mp.send.message.material.temporary.add;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.RequestHead;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.material.entity.MediaType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * @author zhaojn1
 * @version AddTempMaterialOutputMessage.java, v 0.1 2018/3/19 zhaojn1
 */
@Getter
@Setter
@WeChartMessage(requestType = WeChartMessageFormat.MULTIPART, requestMethod = WeChartMessageRequestMethod.POST,
        requestHead = {@RequestHead(key = "Content-Type", value = "multipart/form-data")})
public class AddTempMaterialOutputMessage extends BaseMpSendOutputMessage<AddTempMaterialInputMessage> {

    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

    @JsonIgnore
    private MediaType type;

    private File media;

    @Override
    public String getUrl() {
        return String.format(REQUEST_URL, accessToken, type.getCode());
    }

}
