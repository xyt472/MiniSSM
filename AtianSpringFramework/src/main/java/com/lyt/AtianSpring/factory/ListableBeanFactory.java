package com.lyt.AtianSpring.factory;

import com.lyt.AtianSpring.config.BeansException;

import java.util.List;
import java.util.Map;

/**
 * 对于Bean容器中的Bean可以进行集合操作或者说叫批量操作
 */
public interface ListableBeanFactory extends BeanFactory{
    /**
     * 可以根据指定类型获取它或者它实现类的对象
     * @param type
     * @return
     */
    <T> List<T> getBeansByType(Class type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * Return the names of all beans defined in this registry.
     *
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}
