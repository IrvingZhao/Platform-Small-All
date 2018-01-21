package cn.irving.zhao.web;

import cn.irving.zhao.platform.core.dao.CustomMapper;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by irvin on 2018/1/21.
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableTransactionManagement
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("cn.irving.*");
        mapperScannerConfigurer.setMarkerInterface(CustomMapper.class);
//        Properties properties = new Properties();
//        properties.setProperty("mappers", "cn.irving.zhao.platform.core.dao.CustomMapper");
//        properties.setProperty("notEmpty", "false");
//        properties.setProperty("IDENTITY", "MYSQL");
//        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

    @Bean
    public TransactionInterceptor txAdvice(PlatformTransactionManager transactionManager) {
        NameMatchTransactionAttributeSource attrs = new NameMatchTransactionAttributeSource();

        Properties transactionProperties = new Properties();
        transactionProperties.setProperty("save*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionProperties.setProperty("insert*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionProperties.setProperty("add*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionProperties.setProperty("update*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionProperties.setProperty("delete*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
        transactionProperties.setProperty("*", "readOnly");

        attrs.setProperties(transactionProperties);

        return new TransactionInterceptor(transactionManager, attrs);
    }

    @Bean
    public Advisor transactionAdvisor(TransactionInterceptor txAdvice) {
        DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
        advisor.setAdvice(txAdvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* cn.irving.zhao..service.*.*(..))");
        advisor.setPointcut(pointcut);
        return advisor;
    }

}
