package cn.irving.zhao.platform.weixin.mp.config.enums;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageEventType implements CustomEnumValue<MessageEventType, String> {
    /**
     * 模板消息
     */
    TEMPLATE("TEMPLATESENDJOBFINISH"),
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    SCAN("SCAN"),
    LOCATION("LOCATION"),
    CLICK("CLICK"),
    VIEW("VIEW"),
    ;
    private String code;

    @Override
    public MessageEventType[] getValues() {
        return MessageEventType.values();
    }
}
