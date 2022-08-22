package com.lyt.AtianSpring.context.support;


import com.lyt.AtianSpring.config.BeanFactoryPostProcessor;
import com.lyt.AtianSpring.config.BeanPostProcessor;
import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.context.ApplicationEvent;
import com.lyt.AtianSpring.context.ApplicationListener;
import com.lyt.AtianSpring.context.ConfigurableApplicationContext;
import com.lyt.AtianSpring.context.event.ApplicationEventMulticaster;
import com.lyt.AtianSpring.context.event.ContextClosedEvent;
import com.lyt.AtianSpring.context.event.ContextRefreshedEvent;
import com.lyt.AtianSpring.context.event.SimpleApplicationEventMulticaster;
import com.lyt.AtianSpring.factory.ConfigurableListableBeanFactory;

import java.util.Collection;
import java.util.Map;

/**
 * interface. Doesn't mandate the type of storage used for configuration; simply
 * implements common context functionality. Uses the Template Method design pattern,
 * requiring concrete subclasses to implement abstract methods.
 * <p>
 * 抽象应用上下文

 */
public abstract class AbstractApplicationContext  implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {

        System.out.println("*************************容器刷新************************************");

        // 1. 创建 BeanFactory，并加载 BeanDefinition  这里就已经创建了bean 获得了beanFactroy
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        //返回的就是AbstractRefreshableApplicationContext中的beanfactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        //参数是ApplicationContext applicationContext

        //注册了 一个 BeanPostProcessor 但是病没有去执行   传进去的是   AbstractApplicationContext
        // 注册进去之后  postProcessAfterInitialization() 和 postProcessBeforeInitialization 才能生效
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //同时也在这里完成了 BeanPostProcessor的创建
        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        System.out.println("在AbstractApplicationContext中输出已经注册好的beanPostProcessor");
        for (BeanPostProcessor beanPostProcessor : beanFactory.getBeanPostProcessorS()) {
            System.out.println("输出已经注册好的beanPostProcessor"  +beanPostProcessor.getClass().getName());
        }



        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();   //这里又调用getbean  将类中的每个类实例化

        // 9. 发布容器刷新完成事件
        finishRefresh();
    }
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }
    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {

        //在这里完成了BeanFactoryPostProcessor的实例化？
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
            for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
                beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
            }

    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);

       if(beanPostProcessorMap!=null){
           for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
               System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>注册beanPostProcessor  类型是"+beanPostProcessor.getClass().getName());
               beanFactory.addBeanPostProcessor(beanPostProcessor);
               System.out.println("注册beanPostProcessor成功");
           }
       }else {
           System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<registerBeanPostProcessors中beanPostProcessorMap为空");
       }

    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
       // beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

//    private void finishRefresh() {
//        publishEvent(new ContextRefreshedEvent(this));
//    }

//    @Override
//    public void publishEvent(ApplicationEvent event) {
//        applicationEventMulticaster.multicastEvent(event);
//    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }
    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

//    @Override
//    public Object getBean(String name, Object... args) throws BeansException {
//        return getBeanFactory().getBean(name, args);
//    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }

}
