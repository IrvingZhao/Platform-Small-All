package cn.irving.zhao.platform.weixin.mp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserInfo {

    private String openId;
    private String nickName;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String headImgUrl;
    private List<String> privilege;
    private String unionId;

}
