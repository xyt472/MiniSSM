package com.lyt.AtianSpring.factory;


import com.lyt.AtianSpring.config.BeansException;

/**
 * Interface to be implemented by beans that wish to be aware of their
 * owning {@link BeanFactory}.
 *
 * 实现此接口，既能感知到所属的 BeanFactory
 *

 */
public interface BeanFactoryAware extends Aware {

   void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
