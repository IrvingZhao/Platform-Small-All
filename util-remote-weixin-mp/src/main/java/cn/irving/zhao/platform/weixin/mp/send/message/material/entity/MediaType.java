package cn.irving.zhao.platform.weixin.mp.send.message.material.entity;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;

/**
 * 素材类型
 */
public enum MediaType implements CustomEnumValue<MediaType, String> {
    IMAGE, VOICE, VIDEO, THUMB, NEWS;

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
