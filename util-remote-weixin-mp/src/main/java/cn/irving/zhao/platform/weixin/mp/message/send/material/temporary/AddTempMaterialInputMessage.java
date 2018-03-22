package cn.irving.zhao.platform.weixin.mp.message.send.material.temporary;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhaojn1
 * @version AddTempMaterialInputMessage.java, v 0.1 2018/3/19 zhaojn1
 * @project userProfile
 */
public class AddTempMaterialInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("media_id")
    private String mediaId;

    @JsonProperty("create_at")
    private String createAt;

    public String getMediaId() {
        return mediaId;
    }

    public AddTempMaterialInputMessage setMediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public String getCreateAt() {
        return createAt;
    }

    public AddTempMaterialInputMessage setCreateAt(String createAt) {
        this.createAt = createAt;
        return this;
    }
}
