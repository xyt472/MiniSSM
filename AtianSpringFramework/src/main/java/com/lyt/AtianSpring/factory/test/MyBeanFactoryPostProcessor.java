package com.lyt.AtianSpring.factory.test;

import com.lyt.AtianSpring.config.*;
import com.lyt.AtianSpring.factory.ConfigurableListableBeanFactory;


public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        System.out.println("beanFactory.getBeanDefinitionNames().length;长度是"+beanFactory.getBeanDefinitionNames().length);
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
//       List<PropertyValue> PropertyValues  = beanDefinition.getPropertyValues();
//        PropertyValues.add(new PropertyValue("company", "改为：字节跳动 "));
    }
}
