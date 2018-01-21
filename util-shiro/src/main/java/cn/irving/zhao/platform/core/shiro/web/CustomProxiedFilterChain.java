package cn.irving.zhao.platform.core.shiro.web;

import cn.irving.zhao.platform.core.shiro.filters.MatchOneFilter;
import cn.irving.zhao.platform.core.shiro.redirect.LoginUrlGenerator;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.servlet.ProxiedFilterChain;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 扩展Chain
 */
public class CustomProxiedFilterChain implements FilterChain {

    private static final Logger log = LoggerFactory.getLogger(ProxiedFilterChain.class);

    private PatternMatcher pathMatcher = new AntPathMatcher();

    private static Method accessAllowMethod = null;// isAccessAllow 方法 反射信息
    private static Method accessDeniedMethod = null;// onAccessDenied 方法 反射信息

    private static Field appliedPathsField = null;// appliedPaths 属性 反射信息

    static {
        try {
            //获取相关反射信息
            accessAllowMethod = AccessControlFilter.class.getDeclaredMethod("isAccessAllowed", ServletRequest.class, ServletResponse.class, Object.class);
            accessDeniedMethod = AccessControlFilter.class.getDeclaredMethod("onAccessDenied", ServletRequest.class, ServletResponse.class, Object.class);

            appliedPathsField = PathMatchingFilter.class.getDeclaredField("appliedPaths");

            accessAllowMethod.setAccessible(true);//设置权限
            accessDeniedMethod.setAccessible(true);//设置权限

            appliedPathsField.setAccessible(true);
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private FilterChain orig;
    private List<Filter> filters;
    private int index = 0;

    private boolean matchOne = false;

    private AccessControlFilter firstFalseFilter;//第一个出错的filter
    private Object firstFalseFilterConfig;

    private LoginUrlGenerator urlGenerator;

    private String loginUrl;

    private boolean execSuccess = false;//执行成功

    public CustomProxiedFilterChain(FilterChain orig, List<Filter> filters, LoginUrlGenerator urlGenerator) {
        if (orig == null) {
            throw new NullPointerException("original FilterChain cannot be null.");
        }
        this.orig = orig;
        this.filters = filters;
        this.index = 0;
        matchOne = filters != null && !filters.isEmpty() && MatchOneFilter.class.isInstance(filters.get(0));//检查第一个是否为 MatchOneFilter 对象
        this.urlGenerator = urlGenerator;
    }

    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (matchOne) {
            matchOneFilter(request, response);
        } else {
            matchAllFilter(request, response);
        }
    }

    /**
     * 原有 match所有的filter的方法
     */
    private void matchAllFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (this.filters == null || this.filters.size() == this.index) {
            if (log.isTraceEnabled()) {
                log.trace("Invoking original filter chain.");
            }
            this.orig.doFilter(request, response);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Invoking wrapped filter at index [" + this.index + "]");
            }
            setLoginUrl(this.filters.get(this.index++), request, response).doFilter(request, response, this);
        }
    }

    /**
     * 匹配一个即通过的方法
     */
    private void matchOneFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        for (; this.index < this.filters.size(); ) {
            Filter filter = this.filters.get(this.index++);//检查filter
            setLoginUrl(filter, request, response);//设置登陆地址
            if (AccessControlFilter.class.isInstance(filter)) {
                AccessControlFilter accFilter = (AccessControlFilter) filter;
                if (accFilter.isEnabled()) {
                    try {
                        Object pathConfig = getFilterConfig((Map<String, Object>) appliedPathsField.get(accFilter), request);//获取filter的config内容
                        Boolean access = (Boolean) accessAllowMethod.invoke(filter, request, response, pathConfig);
                        if (access) {//只有一个通过，则设置允许通过
                            execSuccess = true;
                        } else {
                            if (firstFalseFilter == null) {//获取第一个不通过的filter及配置path配置信息
                                firstFalseFilter = accFilter;
                                firstFalseFilterConfig = pathConfig;
                            }
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                filter.doFilter(request, response, this);
                return;
            }
            if (this.index == this.filters.size()) {
                if (execSuccess) {
                    this.orig.doFilter(request, response);
                } else {
                    try {
                        accessDeniedMethod.invoke(firstFalseFilter, request, response, firstFalseFilterConfig);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Filter setLoginUrl(Filter filter, ServletRequest request, ServletResponse response) {

        if (urlGenerator != null &&
                AccessControlFilter.class.isInstance(filter) &&
                HttpServletRequest.class.isInstance(request) &&
                HttpServletResponse.class.isInstance(response)) {
            if (loginUrl == null) {
                loginUrl = urlGenerator.getRedirectUrl((HttpServletRequest) request, (HttpServletResponse) response);
            }
            ((AccessControlFilter) filter).setLoginUrl(loginUrl);
        }

        return filter;
    }

    /**
     * 获得path对应的配置信息
     *
     * @param appliedPaths 配置项集合
     * @param request      请求
     */
    private Object getFilterConfig(Map<String, Object> appliedPaths, ServletRequest request) {
        for (String path : appliedPaths.keySet()) {
            // If the path does match, then pass on to the subclass implementation for specific checks
            //(first match 'wins'):
            if (pathsMatch(path, request)) {
                return appliedPaths.get(path);
            }
        }
        return null;
    }

    private boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = getPathWithinApplication(request);
        return pathsMatch(path, requestURI);
    }

    private String getPathWithinApplication(ServletRequest request) {
        return WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
    }

    private boolean pathsMatch(String pattern, String path) {
        return pathMatcher.matches(pattern, path);
    }

}
