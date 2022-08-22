package com.lyt.AtianSpring.factory;



import com.lyt.AtianSpring.config.BeanDefinition;
import com.lyt.AtianSpring.config.BeansException;

import java.lang.reflect.Constructor;

/**
 * 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * 公众号：bugstack虫洞栈
 * Create by 小傅哥(fustack)
 * <p>
 * Bean 实例化策略
 */
//定义实例化策略接口
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
