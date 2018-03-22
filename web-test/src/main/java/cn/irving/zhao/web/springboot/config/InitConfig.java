package cn.irving.zhao.web.springboot.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.*;

import javax.annotation.Resource;

/**
 * Created by irvin on 2018/1/3.
 */
@Configuration
//@ComponentScan(basePackages = {"cn.irving.zhao.**.config"})
//@ComponentScan(basePackages = {"cn.irving.zhao.web"}, includeFilters = @ComponentScan.Filter(controller.class))
//@ImportResource(value = {"classpath*:config/datasource.properties"},reader = PropertiesBeanDefinitionReader.class)
//@PropertySource(name = "ds",value = {"classpath:/config/datasource.properties"})
@PropertySource(value = "classpath:/config/mybatis.properties")
public class InitConfig {

//    @Value("${jdbc.driverClass}")
//    private String mybatis;

    @Resource
    private SqlSessionFactory sqlSession;
//
    @Bean(value = "demoObjectTest")
    Object testObje() {
        return new Object();
    }

}
