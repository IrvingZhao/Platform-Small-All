package cn.irving.zhao.platform.core.shiro.matcher;

import cn.irving.zhao.util.base.security.CryptoUtils;
import cn.irving.zhao.util.base.security.MessageDigestSecurity;
import cn.irving.zhao.platform.core.shiro.authenInfo.PasswordAuthenticationInfo;
import cn.irving.zhao.platform.core.shiro.token.PasswordToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class PasswordCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        if (!PasswordToken.class.isInstance(token) || !PasswordAuthenticationInfo.class.isInstance(info)) {
            throw new ShiroException("token或info类型不匹配");
        }

        PasswordToken passwordToken = (PasswordToken) token;
        PasswordAuthenticationInfo authInfo = (PasswordAuthenticationInfo) info;

        if (StringUtils.isBlank(authInfo.getSalt())) {//不包含 salt 则使用 MD5 进行校验
            return MessageDigestSecurity.MD5.validate(passwordToken.getCredentials(), authInfo.getCredentials());
        } else {// 包含salt 的，使用 CryptoUtils 进行校验
            return CryptoUtils.verify(authInfo.getCredentials(), passwordToken.getCredentials(), authInfo.getSalt());
        }
    }
}
