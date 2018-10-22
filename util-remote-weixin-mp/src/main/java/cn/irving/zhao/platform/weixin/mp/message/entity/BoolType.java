package cn.irving.zhao.platform.weixin.mp.message.entity;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum  BoolType implements CustomEnumValue<BoolType,Integer> {
    TRUE(1),FALSE(0);

    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public BoolType[] getValues() {
        return BoolType.values();
    }
}
