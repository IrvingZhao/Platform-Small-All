package cn.irving.zhao.platform.core.freemarker;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 在页面中加入绝对路径
 */
public class ExtFreeMarkerView extends FreeMarkerView {

    private static final String CONTEXT_PATH = "ctx";

    @Override
    protected void exposeHelpers(Map<String, Object> model,
                                 HttpServletRequest request) throws Exception {
        model.put(CONTEXT_PATH, request.getContextPath()); // 上下文
        model.put("requestUrl", request.getRequestURI()); // 访问地址url
        super.exposeHelpers(model, request);
    }
}
