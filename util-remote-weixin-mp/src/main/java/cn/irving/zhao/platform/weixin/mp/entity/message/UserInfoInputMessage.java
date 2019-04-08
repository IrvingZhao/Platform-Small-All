package cn.irving.zhao.platform.weixin.mp.entity.message;

import cn.irving.zhao.platform.weixin.mp.entity.UserInfo;
import cn.irving.zhao.platform.weixin.mp.message.send.BaseMpSendInputMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoInputMessage extends BaseMpSendInputMessage {

    @JsonProperty("openid")
    private String openId;
    @JsonProperty("nickname")
    private String nickName;
    private String sex;
    private String country;
    private String province;
    private String city;
    @JsonProperty("headimgurl")
    private String headImgUrl;
    private List<String> privilege;
    @JsonProperty("unionid")
    private String unionId;

    public UserInfo toUserInfo() {
        return new UserInfo(
                this.openId,
                this.nickName,
                this.sex,
                this.country,
                this.province,
                this.city,
                this.headImgUrl,
                this.privilege,
                this.unionId
        );
    }

}
