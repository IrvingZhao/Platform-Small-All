package cn.irving.zhao.platform.weixin.mp.message.send.material.entity;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;

public enum MediaType implements CustomEnumValue<MediaType, String> {
    IMAGE, VOICE, VIDEO, THUMB;

    MediaType() {
        this.code = this.name().toLowerCase();
    }

    private String code;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public MediaType[] getValues() {
        return MediaType.values();
    }
}