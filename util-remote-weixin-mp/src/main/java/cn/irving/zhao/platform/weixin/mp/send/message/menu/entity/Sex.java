package cn.irving.zhao.platform.weixin.mp.send.message.menu.entity;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;

/**
 * 性别
 */
public enum Sex implements CustomEnumValue<Sex, Integer> {
    Male(1), Female(2);
    private int code;

    Sex(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public Sex[] getValues() {
        return Sex.values();
    }
}
