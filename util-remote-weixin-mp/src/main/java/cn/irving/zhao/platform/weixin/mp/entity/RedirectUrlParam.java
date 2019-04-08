package cn.irving.zhao.platform.weixin.mp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectUrlParam {
    private String redirectUrl;
    private AuthScope authScope;
    private String state;

    /**
     * 授权范围，如果需获取 union id，则需使用 USER_INFO，获取用户token后，再次获取用户信息
     */
    @Getter
    @AllArgsConstructor
    public enum AuthScope {
        BASE("snsapi_base"), USER_INFO("snsapi_userinfo");
        private String code;
    }
}
