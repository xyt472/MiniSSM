package com.lyt.AtianSpring.reader;

import com.lyt.AtianSpring.registry.BeanDefinitionRegistry;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
    private final BeanDefinitionRegistry registry;
//    private ResourceLoader resourceLoader;
//


    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;

    }





}
