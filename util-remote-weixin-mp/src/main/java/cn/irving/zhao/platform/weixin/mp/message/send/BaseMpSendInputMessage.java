package cn.irving.zhao.platform.weixin.mp.message.send;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 公众号-发送消息-输入消息 基础类
 */
public abstract class BaseMpSendInputMessage extends BaseSendInputMessage {

    @JsonProperty("errcode")
    private String errCode;

    @JsonProperty("errmsg")
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
