package cn.irving.zhao.platform.weixin.mp.send.message.material.permanent.count;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;

@WeChartMessage(requestType = WeChartMessageFormat.JSON, requestMethod = WeChartMessageRequestMethod.GET)
public class MaterialCountOutputMessage extends BaseMpSendOutputMessage<MaterialCountInputMessage> {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=%s";

    @Override
    public String getUrl() {
        return String.format(REQUEST_URL, super.accessToken);
    }
}
