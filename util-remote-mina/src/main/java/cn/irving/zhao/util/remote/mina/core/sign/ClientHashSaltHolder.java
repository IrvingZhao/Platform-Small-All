package cn.irving.zhao.util.remote.mina.core.sign;

/**
 * 客户端对应salt保存器
 */
@FunctionalInterface
public interface ClientHashSaltHolder {

    /**
     * 获得加密salt
     */
    String getSalt(String clientKey);

}
