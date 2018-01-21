package cn.irving.zhao.platform.core.shiro.config;

public interface ThirdPlatform {

    /**
     * 根据授权码获得第三方平台用户id
     */
    String getThirdPlatformUserId(String authCode);

    /**
     * 获得第三方平台编码
     */
    String getThirdPlatformCode();

}
