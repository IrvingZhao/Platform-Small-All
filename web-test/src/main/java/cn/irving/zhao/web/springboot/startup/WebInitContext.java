package cn.irving.zhao.web.springboot.startup;

import cn.irving.zhao.web.springboot.config.InitConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by irvin on 2018/1/3.
 */
public class WebInitContext implements WebApplicationInitializer {
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        System.out.println("init");

        AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext();
        context.register(InitConfig.class);

        //添加linstener
        servletContext.addListener(new ContextLoaderListener(context));

        //添加servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }
}
