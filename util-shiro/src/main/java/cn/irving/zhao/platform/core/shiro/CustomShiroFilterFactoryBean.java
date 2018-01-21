package cn.irving.zhao.platform.core.shiro;

import cn.irving.zhao.platform.core.shiro.mgt.DefaultFilterChainManagerProxy;
import cn.irving.zhao.platform.core.shiro.redirect.LoginUrlGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;

/**
 * 自定义shiro过滤器工厂
 */
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    /**
     * 权限一个通过即可
     */
    private Boolean enableSingleMatch = true;

    private LoginUrlGenerator urlGenerator;

    @Override
    protected FilterChainManager createFilterChainManager() {

        FilterChainManager defaultManager = super.createFilterChainManager();
        if (enableSingleMatch) {
            return new DefaultFilterChainManagerProxy(defaultManager, urlGenerator);
        } else {
            return defaultManager;
        }
    }

    public Boolean getEnableSingleMatch() {
        return enableSingleMatch;
    }

    public void setEnableSingleMatch(Boolean enableSingleMatch) {
        this.enableSingleMatch = enableSingleMatch;
    }

    public LoginUrlGenerator getUrlGenerator() {
        return urlGenerator;
    }

    public void setUrlGenerator(LoginUrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }
}
