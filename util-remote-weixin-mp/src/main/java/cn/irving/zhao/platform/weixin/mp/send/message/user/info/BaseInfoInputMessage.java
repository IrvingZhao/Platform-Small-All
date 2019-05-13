package cn.irving.zhao.platform.weixin.mp.send.message.user.info;

import cn.irving.zhao.platform.weixin.mp.send.message.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BaseInfoInputMessage extends BaseMpSendInputMessage {

    private int subscribe;

    @JsonProperty("openid")
    private String openId;

    private String nickname;

    private int sex;

    private String language;
    private String city;
    private String province;
    private String country;
    @JsonProperty("headimgurl")
    private String headImgUrl;

    @JsonProperty("subscribe_time")
    private long subscribeTime;

    @JsonProperty("unionid")
    private String unionId;

    private String remark;

    @JsonProperty("groupid")
    private int groupId;

    @JsonProperty("tagid_list")
    private List<Integer> tagIdList;

    @JsonProperty("subscribe_scene")
    private String subscribeScene;

    @JsonProperty("qr_scene")
    private String qrScene;

    @JsonProperty("qr_scene_str")
    private String qrSceneStr;

    @JsonIgnore
    public Date getSubscribeTimeDate() {
        return new Date(this.subscribe * 1000);
    }

}
