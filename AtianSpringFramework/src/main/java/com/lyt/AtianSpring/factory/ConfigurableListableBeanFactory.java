package com.lyt.AtianSpring.factory;


import com.lyt.AtianSpring.config.BeanDefinition;
import com.lyt.AtianSpring.config.BeanPostProcessor;
import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.config.ConfigurableBeanFactory;

import java.util.List;

/**
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 *

 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowiredCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    List<BeanPostProcessor> getBeanPostProcessorS();
}
