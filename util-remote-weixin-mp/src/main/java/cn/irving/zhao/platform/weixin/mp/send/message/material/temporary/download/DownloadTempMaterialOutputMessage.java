package cn.irving.zhao.platform.weixin.mp.send.message.material.temporary.download;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendOutputMessage;

/**
 * 获取临时素材
 */
public class DownloadTempMaterialOutputMessage extends BaseMpSendOutputMessage<DownloadTempMaterialInputMessage> {
    @Override
    public String getUrl() {
        return null;
    }
    //TODO 获取临时素材涉及 下载  暂缓

}
