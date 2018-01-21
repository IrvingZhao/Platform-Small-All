package cn.irving.zhao.platform.core.shiro.realm;

import cn.irving.zhao.platform.core.shiro.user.ShiroUser;
import cn.irving.zhao.platform.core.shiro.user.ShiroUserService;
import cn.irving.zhao.platform.core.shiro.authenInfo.PasswordAuthenticationInfo;
import cn.irving.zhao.platform.core.shiro.token.PasswordToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class PasswordAuthorizingRealm extends AuthorizingRealm {

    @Resource
    private ShiroUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前Realm的授权用户信息
        for (Object o : principals.fromRealm(getName())) {
            ShiroUser userModel = (ShiroUser) o;
            //perms
            //添加用户所具备的权限
            info.addRoles(userModel.getRoles());
            info.addStringPermissions(userModel.getPerms());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        PasswordToken passwordToken = (PasswordToken) token;
        if (StringUtils.isBlank(passwordToken.getPrincipal())) {
            throw new AccountException("用户名不能为空");
        }
        ShiroUser userInfo = userService.getUserInfoByUserName(passwordToken.getPrincipal());
        if (userInfo == null) {
            throw new UnknownAccountException("用户[" + passwordToken.getPrincipal() + "]不存在");
        }
        return new PasswordAuthenticationInfo(userInfo, userInfo.getPassword(), userInfo.getSalt(), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return PasswordToken.class.isInstance(token);
    }
}
