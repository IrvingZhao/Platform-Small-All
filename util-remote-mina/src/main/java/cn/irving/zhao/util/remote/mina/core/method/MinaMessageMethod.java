package cn.irving.zhao.util.remote.mina.core.method;

/**
 * mina 消息方法
 */
public interface MinaMessageMethod<T, R> {

    /**
     * 执行方法
     */
    R execute(T data);

    /**
     * 获得数据类型，作为反序列化对象使用
     */
    Class<T> getDataType();

}
