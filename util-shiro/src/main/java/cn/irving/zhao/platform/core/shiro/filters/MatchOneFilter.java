package cn.irving.zhao.platform.core.shiro.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * 只要有一个通过过滤器通过，则全部通过，仅作为标注使用
 */
public class MatchOneFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
