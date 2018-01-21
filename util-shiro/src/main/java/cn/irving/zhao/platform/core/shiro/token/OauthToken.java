package cn.irving.zhao.platform.core.shiro.token;

import cn.irving.zhao.platform.core.shiro.config.ThirdPlatform;
import cn.irving.zhao.platform.core.shiro.config.OauthLoginType;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Oauth服务登陆Token
 */
public final class OauthToken implements AuthenticationToken {

    /**
     * @param authCode 第三方登陆授权码
     * @param platform 第三方平台
     */
    public OauthToken(String authCode, ThirdPlatform platform) {
        this.loginCode = authCode;
        this.platform = platform;
        this.loginType = OauthLoginType.AUTH_CODE;
    }

    /**
     * @param loginCode 登陆码
     * @param platform  登陆平台
     * @param loginType 登陆方式
     */
    public OauthToken(String loginCode, ThirdPlatform platform, OauthLoginType loginType) {
        this.loginCode = loginCode;
        this.platform = platform;
        this.loginType = loginType;
    }

    private final String loginCode;//登陆code

    private final ThirdPlatform platform;//登陆平台

    private final OauthLoginType loginType;//授权码  或  第三方id

    /**
     * 获得授权平台信息
     */
    @Override
    public ThirdPlatform getPrincipal() {
        return platform;
    }

    /**
     * 获得登陆code
     */
    @Override
    public String getCredentials() {
        return loginCode;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public ThirdPlatform getPlatform() {
        return platform;
    }

    public OauthLoginType getLoginType() {
        return loginType;
    }
}
