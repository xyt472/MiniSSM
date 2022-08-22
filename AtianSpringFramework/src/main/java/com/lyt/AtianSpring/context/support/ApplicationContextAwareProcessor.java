package com.lyt.AtianSpring.context.support;


import com.lyt.AtianSpring.config.BeanPostProcessor;
import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.context.ApplicationContext;
import com.lyt.AtianSpring.context.ApplicationContextAware;


public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        System.out.println("-=-=-=-=-this=-=-=-=当前对象是  ："+applicationContext.getClass().getName());
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
