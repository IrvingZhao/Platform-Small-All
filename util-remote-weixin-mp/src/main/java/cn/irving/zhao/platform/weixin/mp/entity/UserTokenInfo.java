package cn.irving.zhao.platform.weixin.mp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserTokenInfo {

    private final String accessToken;

    private final Long expiresIn;

    private final String refreshToken;

    private final String openId;

    private final String scope;

}
