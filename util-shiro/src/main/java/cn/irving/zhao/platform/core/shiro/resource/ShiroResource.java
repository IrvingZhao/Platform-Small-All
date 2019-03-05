package cn.irving.zhao.platform.core.shiro.resource;

import java.util.Map;

public interface ShiroResource {

    /**
     * 请求地址
     * @return 资源匹配地址
     */
    String getPath();

    /**
     * 获取资源所需权限
     * <pre>
     *     数据结构：
     *     {
     *         roles:"角色A,角色B",
     *         perms:"per:query"
     *     }
     *     等同于在配置文件中写，当前资源= roles["角色A","角色B"],perms["per:query"]
     * </pre>
     * @return 资源所需权限
     */
    Map<String, String> getPerms();

}
