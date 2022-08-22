package com.lyt.AtianSpring.context;


import com.lyt.AtianSpring.factory.HierarchicalBeanFactory;
import com.lyt.AtianSpring.factory.ListableBeanFactory;
import com.lyt.AtianSpring.resource.Resource;

/**
 * 应用上下文
 */
public interface ApplicationContext extends ListableBeanFactory, Resource , ApplicationEventPublisher , HierarchicalBeanFactory {
}
