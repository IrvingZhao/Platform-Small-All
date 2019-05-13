package cn.irving.zhao.platform.weixin.mp.send.message.material.permanent.delete;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@WeChartMessage(requestType = WeChartMessageFormat.JSON, requestMethod = WeChartMessageRequestMethod.POST)
public class MaterialDeleteOutputMessage extends BaseMpSendOutputMessage<MaterialDeleteInputMessage> {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=%s";

    @Override
    public String getUrl() {
        return String.format(REQUEST_URL, super.accessToken);
    }

    private String mediaId;
}
