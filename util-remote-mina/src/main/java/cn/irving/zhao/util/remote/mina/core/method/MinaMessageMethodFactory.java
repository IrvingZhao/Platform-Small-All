package cn.irving.zhao.util.remote.mina.core.method;


/**
 * mina 消息执行器 工厂
 */
public interface MinaMessageMethodFactory<T, R> {

    /**
     * 根据方法名获得方法执行器
     */
    MinaMessageMethod<T, R> getMethod(String methodName);

    /**
     * 注册方法执行器
     */
    void registerMethod(String methodName, MinaMessageMethod<T, R> method);

}
