package cn.irving.zhao.util.remote.mina.core.serial;

/**
 * 数据序列化执行器
 */
public interface MinaMessageSerialExecutor {

    /**
     * 序列化对象
     *
     * @param data 原始数据
     * @return 序列化后的结果
     */
    String serial(Object data);

    /**
     * 反序列化对象
     *
     * @param data 序列化后的数据
     * @return 解析后的对象
     */
    <T> T parse(String data, Class<T> type);

}
