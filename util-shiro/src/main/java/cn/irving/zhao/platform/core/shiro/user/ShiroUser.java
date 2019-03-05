package cn.irving.zhao.platform.core.shiro.user;

import java.util.Collection;
import java.util.Map;

public interface ShiroUser {

    String getUserId();

    String getPassword();

    String getSalt();

    /**
     * 用户具备的角色
     *
     * @return 用户角色
     */
    Collection<String> getRoles();

    /**
     * 获得用户所具备的权限
     *
     * @return 用户权限
     */
    Collection<String> getPerms();

}
