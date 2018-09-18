package cn.irving.zhao.platform.weixin.mp.message.send.material.list;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendOutputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.material.entity.MediaType;

public class ListMaterialOutputMessage extends BaseMpSendOutputMessage<ListMaterialInputMessage> {
    private static final String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";

    @Override
    public String getUrl() {
        return String.format(url, accessToken);
    }

    private MediaType type;
    private int offset;
    private int count;

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
