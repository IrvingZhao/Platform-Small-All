package cn.irving.zhao.platform.core.constract;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;

/**
 * 是否枚举类
 */
public enum TrueFalseEnum implements CustomEnumValue<TrueFalseEnum, Boolean> {
    Y(Boolean.TRUE), N(Boolean.FALSE);

    TrueFalseEnum(Boolean code) {
        this.code = code;
    }

    private Boolean code;

    @Override
    public Boolean getCode() {
        return code;
    }

    @Override
    public TrueFalseEnum[] getValues() {
        return TrueFalseEnum.values();
    }
}
