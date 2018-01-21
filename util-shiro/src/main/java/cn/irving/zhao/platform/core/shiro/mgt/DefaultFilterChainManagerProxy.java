package cn.irving.zhao.platform.core.shiro.mgt;

import cn.irving.zhao.platform.core.shiro.redirect.LoginUrlGenerator;
import cn.irving.zhao.platform.core.shiro.web.CustomProxiedFilterChain;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import java.util.Map;
import java.util.Set;

public class DefaultFilterChainManagerProxy implements FilterChainManager {

    private FilterChainManager defaultFilterChainManager;

    private LoginUrlGenerator urlGenerator;

    public DefaultFilterChainManagerProxy(FilterChainManager defaultFilterChainManager, LoginUrlGenerator urlGenerator) {
        this.defaultFilterChainManager = defaultFilterChainManager;
        this.urlGenerator = urlGenerator;
    }

    @Override
    public FilterChain proxy(FilterChain original, String chainName) {
        NamedFilterList configured = getChain(chainName);
        if (configured == null) {
            String msg = "There is no configured chain under the name/key [" + chainName + "].";
            throw new IllegalArgumentException(msg);
        }
        return new CustomProxiedFilterChain(original, configured, urlGenerator);//替换新的FilterChain
    }

    @Override
    public Map<String, Filter> getFilters() {
        return defaultFilterChainManager.getFilters();
    }

    @Override
    public NamedFilterList getChain(String chainName) {
        return defaultFilterChainManager.getChain(chainName);
    }

    @Override
    public boolean hasChains() {
        return defaultFilterChainManager.hasChains();
    }

    @Override
    public Set<String> getChainNames() {
        return defaultFilterChainManager.getChainNames();
    }

    @Override
    public void addFilter(String name, Filter filter) {
        defaultFilterChainManager.addFilter(name, filter);
    }

    @Override
    public void addFilter(String name, Filter filter, boolean init) {
        defaultFilterChainManager.addFilter(name, filter, init);
    }

    @Override
    public void createChain(String chainName, String chainDefinition) {
        defaultFilterChainManager.createChain(chainName, chainDefinition);
    }

    @Override
    public void addToChain(String chainName, String filterName) {
        defaultFilterChainManager.addToChain(chainName, filterName);
    }

    @Override
    public void addToChain(String chainName, String filterName, String chainSpecificFilterConfig) throws ConfigurationException {
        defaultFilterChainManager.addToChain(chainName, filterName, chainSpecificFilterConfig);
    }
}
