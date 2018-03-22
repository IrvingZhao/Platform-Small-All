package cn.irving.zhao.platform.weixin.mp.config.enums;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;

public enum ReceiverMessageType implements CustomEnumValue<ReceiverMessageType, String> {
    TEXT, IMAGE, VOICE, VIDEO, MUSIC, NEWS, EVENT;

    @Override
    public String getCode() {
        return this.name().toLowerCase();
    }

    @Override
    public ReceiverMessageType getItem(String code) {
        return ReceiverMessageType.valueOf(code.toUpperCase());
    }

    @Override
    public ReceiverMessageType[] getValues() {
        return ReceiverMessageType.values();
    }

}
