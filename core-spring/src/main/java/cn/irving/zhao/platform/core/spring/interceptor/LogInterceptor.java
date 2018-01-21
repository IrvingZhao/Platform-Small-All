package cn.irving.zhao.platform.core.spring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * <p>记录单请求时间</p>
 * <p>声明 RequestTimeLogger 进行请求时间记录</p>
 */
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("RequestTimeLogger");

    private static final ThreadLocal<Long> startTimeThreadLocal =
            new NamedThreadLocal<>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //设置线程开启时间
        startTimeThreadLocal.set(System.currentTimeMillis());
//        if (logger.isDebugEnabled()) {
//            long beginTime = System.currentTimeMillis();//1、开始时间
//            startTimeThreadLocal.set(beginTime);        //线程绑定变量（该数据只有当前请求的线程可见）
//            logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
//                    .format(beginTime), request.getRequestURI());
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            logger.info("ViewName: " + modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();    //2、结束时间
        //日志输出记录访问时间
        logger.info("请求耗时：{\"url\":\"{}\", \"duringTime\":{}, \"endTime\":{}, \"handleClass\":\"{}\", \"exception\":\"{}\" }", request.getRequestURI(), endTime - beginTime, endTime, handler.getClass().getName(), ex == null ? "" : ex.getMessage());

//        if (logger.isDebugEnabled()) {
//            logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
//                    new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtil.dateTimeToString(new Date(endTime - beginTime)),
//                    request.getRequestURI(), Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
//                    (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
//        }

    }

}
