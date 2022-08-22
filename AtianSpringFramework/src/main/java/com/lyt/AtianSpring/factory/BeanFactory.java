package com.lyt.AtianSpring.factory;

import com.lyt.AtianSpring.config.BeansException;

public interface BeanFactory {
    Object getBean(String beanName);
    Object getBean(String name, Object... args) throws BeansException;
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
    <T> T getBean(Class<T> requiredType) throws BeansException;
}
