package cn.irving.zhao.platform.weixin.mp.message.send.material.temporary.add;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.material.entity.MediaType;
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

    @JsonProperty("type")
    private MediaType mediaType;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
