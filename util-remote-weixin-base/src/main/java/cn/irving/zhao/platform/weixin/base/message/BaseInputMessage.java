package cn.irving.zhao.platform.weixin.base.message;

/**
 * 基础输入消息
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseInputMessage<T extends BaseOutputMessage> {

    /**
     * 获得输入消息所对应的输出消息
     */
    public abstract Class<T> getOutputMessageClass();

}
