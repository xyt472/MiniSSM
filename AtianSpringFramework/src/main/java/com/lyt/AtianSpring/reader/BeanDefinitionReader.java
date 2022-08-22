package com.lyt.AtianSpring.reader;


import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.registry.BeanDefinitionRegistry;

import java.io.InputStream;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location);
    BeanDefinitionRegistry getRegistry();
    void loadBeanDefinitions(InputStream resource);
    void loadBeanDefinitions(String... locations) throws BeansException;
}
