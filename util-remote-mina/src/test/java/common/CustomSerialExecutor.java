package common;

import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import cn.irving.zhao.util.remote.mina.core.serial.MinaMessageSerialExecutor;

public class CustomSerialExecutor implements MinaMessageSerialExecutor {

    private ObjectStringSerialUtil serialUtil = ObjectStringSerialUtil.getSerialUtil();

    @Override
    public String serial(Object data) {
        return serialUtil.serial(data, ObjectStringSerialUtil.SerialType.JSON);
    }

    @Override
    public <T> T parse(String data, Class<T> type) {
        return serialUtil.parse(data, type, ObjectStringSerialUtil.SerialType.JSON);
    }
}
