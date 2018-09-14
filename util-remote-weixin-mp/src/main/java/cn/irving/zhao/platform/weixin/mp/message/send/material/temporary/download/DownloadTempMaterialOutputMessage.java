package cn.irving.zhao.platform.weixin.mp.message.send.material.temporary.download;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;

public class DownloadTempMaterialOutputMessage extends BaseMpSendOutputMessage<DownloadTempMaterialInputMessage> {
    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Class<DownloadTempMaterialInputMessage> getInputMessageClass() {
        return DownloadTempMaterialInputMessage.class;
    }
}
