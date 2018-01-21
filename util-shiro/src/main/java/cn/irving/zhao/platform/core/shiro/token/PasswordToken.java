package cn.irving.zhao.platform.core.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用户名，密码使用登录用的token
 */
public final class PasswordToken implements AuthenticationToken {

    public PasswordToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private final String username;
    private final String password;

    /**
     * 获得用户名
     */
    @Override
    public String getPrincipal() {
        return username;
    }

    /**
     * 获得登录密码
     */
    @Override
    public String getCredentials() {
        return password;
    }
}
