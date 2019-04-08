package cn.irving.zhao.platform.weixin.mp.send.message.material.list;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.send.message.material.entity.MediaType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@WeChartMessage(requestType = WeChartMessageFormat.JSON, requestMethod = WeChartMessageRequestMethod.POST)
public class ListMaterialOutputMessage extends BaseMpSendOutputMessage<ListMaterialInputMessage> {
    private static final String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";

    @Override
    public String getUrl() {
        return String.format(url, accessToken);
    }

    private MediaType type;
    private int offset;
    private int count;

    @Override
    public Class<? extends ListMaterialInputMessage> getInputMessageClass() {
        Class<? extends ListMaterialInputMessage> result;
        switch (type) {
            case NEWS:
                result =  ListMaterialNewsInputMessage.class;
                break;
            case IMAGE:
            case THUMB:
            case VIDEO:
            case VOICE:
                result = ListMaterialOtherInputMessage.class;
                break;
            default:
                result = null;
                break;
        }
        return result;
    }
}
