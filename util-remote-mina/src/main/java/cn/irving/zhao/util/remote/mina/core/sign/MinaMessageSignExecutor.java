package cn.irving.zhao.util.remote.mina.core.sign;

/**
 * 消息签名执行器
 */
public interface MinaMessageSignExecutor {

    /**
     * 获得数据的签名
     *
     * @param data 原始数据
     * @param salt 签名盐
     * @return 签名
     */
    String getMessageSign(String data, String salt);

    /**
     * 验证消息签名
     *
     * @param data 原始数据
     * @param sign 签名
     * @param salt 签名盐
     * @return 是否成功
     */
    boolean validMessage(String data, String sign, String salt);

}
