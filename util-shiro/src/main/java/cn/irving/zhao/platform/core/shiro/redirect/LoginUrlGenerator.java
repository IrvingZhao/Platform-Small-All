package cn.irving.zhao.platform.core.shiro.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 重定向地址生成器
 */
public interface LoginUrlGenerator {

    String getRedirectUrl(HttpServletRequest request, HttpServletResponse response);

    /**
     * 拼接url参数，以&amp;开始
     *
     * @param params url跳转参数
     * @return 参数拼接结果
     */
    default String getExtraParam(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        params.entrySet().forEach((item -> {
            builder.append("&").append(item.getKey()).append("=").append(item.getValue());
        }));
        return builder.toString();
    }

}
