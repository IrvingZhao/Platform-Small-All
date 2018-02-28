package cn.irving.zhao.platform.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.*;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

/**
 * 让静态变量保存到Spring ApplicationContext
 * 可在任何代码任何地方任何时候中取出ApplicaitonContext
 */
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {


    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext != null) {
            logger.info("SpringContextUtil中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextUtil.applicationContext);
        }
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static String getRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = getApplicationContext().getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取系统根目录失败");
        }
        return rootRealPath;
    }

    public static String getResourceRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取资源根目录失败");
        }
        return rootRealPath;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }

    @Nullable
    public static String getId() {
        return applicationContext.getId();
    }

    public static String getApplicationName() {
        return applicationContext.getApplicationName();
    }

    public static String getDisplayName() {
        return applicationContext.getDisplayName();
    }

    public static long getStartupDate() {
        return applicationContext.getStartupDate();
    }

    @Nullable
    public static ApplicationContext getParent() {
        return applicationContext.getParent();
    }

    public static AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return applicationContext.getAutowireCapableBeanFactory();
    }

    public static Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }

    public static boolean containsBeanDefinition(String beanName) {
        return applicationContext.containsBeanDefinition(beanName);
    }

    public static int getBeanDefinitionCount() {
        return applicationContext.getBeanDefinitionCount();
    }

    public static String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    public static String[] getBeanNamesForType(ResolvableType type) {
        return applicationContext.getBeanNamesForType(type);
    }

    public static String[] getBeanNamesForType(Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    public static String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return applicationContext.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return applicationContext.getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeanNamesForAnnotation(annotationType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    @Nullable
    public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
        return applicationContext.findAnnotationOnBean(beanName, annotationType);
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static Object getBean(String name, Object... args) throws BeansException {
        return applicationContext.getBean(name, args);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return applicationContext.getBean(requiredType, args);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isPrototype(name);
    }

    public static boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return applicationContext.isTypeMatch(name, typeToMatch);
    }

    public static boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return applicationContext.isTypeMatch(name, typeToMatch);
    }

    @Nullable
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public static String[] getAliases(String name) {
        return applicationContext.getAliases(name);
    }

    @Nullable
    public static BeanFactory getParentBeanFactory() {
        return applicationContext.getParentBeanFactory();
    }

    public static boolean containsLocalBean(String name) {
        return applicationContext.containsLocalBean(name);
    }

    @Nullable
    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return applicationContext.getMessage(code, args, defaultMessage, locale);
    }

    public static String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return applicationContext.getMessage(code, args, locale);
    }

    public static String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return applicationContext.getMessage(resolvable, locale);
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    public static void publishEvent(Object event) {
        applicationContext.publishEvent(event);
    }

    public static Resource[] getResources(String s) throws IOException {
        return applicationContext.getResources(s);
    }

    public static Resource getResource(String s) {
        return applicationContext.getResource(s);
    }

    @Nullable
    public static ClassLoader getClassLoader() {
        return applicationContext.getClassLoader();
    }
}
