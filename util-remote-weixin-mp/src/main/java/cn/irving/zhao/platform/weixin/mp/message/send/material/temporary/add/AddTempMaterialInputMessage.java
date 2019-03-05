package cn.irving.zhao.platform.weixin.mp.message.send.material.temporary.add;

import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.material.entity.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhaojn1
 * @version AddTempMaterialInputMessage.java, v 0.1 2018/3/19 zhaojn1
 */
@Getter
@Setter
public class AddTempMaterialInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("media_id")
    private String mediaId;

    @JsonProperty("create_at")
    private String createAt;

    @JsonProperty("type")
    private MediaType mediaType;

}
