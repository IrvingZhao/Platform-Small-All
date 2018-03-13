package cn.irving.zhao.platform.weixin.base.message;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessage;
import cn.irving.zhao.platform.weixin.base.config.message.WeChartMessageConfig;
import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>基础输出消息</p>
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseOutputMessage<T extends BaseInputMessage> extends BaseMessage {

    @JsonIgnore
    public abstract Class<T> getInputMessageClass();

    private final WeChartMessageConfig messageConfig;

    protected BaseOutputMessage() {
        WeChartMessage weChartMessage = this.getClass().getAnnotation(WeChartMessage.class);
        messageConfig = new WeChartMessageConfig(weChartMessage);
    }

    @JsonIgnore
    public String getSerialContent() {
        String result = null;
        if (messageConfig.getRequestType() == WeChartMessageFormat.JSON) {
            result = serialUtil.serial(this, ObjectStringSerialUtil.SerialType.JSON);
        } else if (messageConfig.getRequestType() == WeChartMessageFormat.XML) {
            result = serialUtil.serial(this, ObjectStringSerialUtil.SerialType.XML);
        }
        return result;
    }

    @JsonIgnore
    public WeChartMessageConfig getMessageConfig() {
        return messageConfig;
    }
}
