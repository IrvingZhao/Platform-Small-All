package cn.irving.zhao.platform.weixin.mp.config.enums;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceiverMessageType implements CustomEnumValue<ReceiverMessageType, String> {
    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    VIDEO("video"),
    SHORT_VIDEO("shortvideo"),
    LOCATION("location"),
    LINK("link"),
    EVENT("event"),
    ;

    private String code;

    @Override
    public ReceiverMessageType[] getValues() {
        return ReceiverMessageType.values();
    }

}
