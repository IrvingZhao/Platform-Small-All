package cn.irving.zhao.platform.weixin.base.message.send;

import cn.irving.zhao.platform.weixin.base.message.BaseOutputMessage;
import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础发送消息-输出消息
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseSendOutputMessage<T extends BaseSendInputMessage> extends BaseOutputMessage {
    @JsonIgnore
    public abstract String getUrl();

    @JsonIgnore
    public abstract Class<T> getInputMessageClass();

    @JsonIgnore
    final Map<String, Object> getParamMap() {
        return serialUtil.parse(serialUtil.serial(this, ObjectStringSerialUtil.SerialType.JSON), ObjectStringSerialUtil.SerialType.JSON);
    }
}
