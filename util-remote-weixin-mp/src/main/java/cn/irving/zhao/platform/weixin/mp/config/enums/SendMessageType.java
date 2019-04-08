package cn.irving.zhao.platform.weixin.mp.config.enums;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SendMessageType implements CustomEnumValue<SendMessageType, String> {
    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    VIDEO("video"),
    MUSIC("music"),
    NEWS("news"),
    ;
    private String code;

    @Override
    public SendMessageType[] getValues() {
        return SendMessageType.values();
    }
}
