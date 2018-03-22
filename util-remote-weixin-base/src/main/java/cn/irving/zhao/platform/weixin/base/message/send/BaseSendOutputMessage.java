package cn.irving.zhao.platform.weixin.base.message.send;

import cn.irving.zhao.platform.weixin.base.message.BaseOutputMessage;
import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
    private Map<String, Field> fieldCache;

    @JsonIgnore
    final Map<String, Object> getParamMap() {
        if (fieldCache == null) {
            Class type = this.getClass();
            fieldCache = new HashMap<>();
            while (type != BaseSendOutputMessage.class) {
                Field[] fields = type.getDeclaredFields();
                Stream.of(fields).filter(item -> item.getAnnotation(JsonIgnore.class) == null)
                        .forEach(item -> {
                            if (Modifier.isFinal(item.getModifiers())) {
                                return;
                            }
                            JsonProperty property = item.getAnnotation(JsonProperty.class);
                            item.setAccessible(true);
                            if (property == null) {
                                fieldCache.put(item.getName(), item);
                            } else {
                                fieldCache.put(property.value(), item);
                            }
                        });
                type = type.getSuperclass();
            }
        }
        Map<String, Object> result = new HashMap<>();
        fieldCache.entrySet().parallelStream().forEach(item -> {
            try {
                result.put(item.getKey(), item.getValue().get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return result;
        //TODO 更改 获取当前对象参数Map方式
    }
}
