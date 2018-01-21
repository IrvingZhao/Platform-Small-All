package cn.irving.zhao.platform.core.shiro.filters;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * 多角色，匹配一个角色，则可访问过滤器
 */
public class MultipleRoleAuthFilter extends AuthorizationFilter {
    /**
     * 验证多个角色可以访问同一个资源
     *
     * @param request     http请求
     * @param response    http响应
     * @param mappedValue 匹配值
     * @return 匹配是否成功
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for (String string : roles) {
            if (subject.hasRole(string)) {
                return true;
            }
        }
        return false;
    }



}
