package com.lyt.AtianSpring.context.support;


import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.factory.ConfigurableListableBeanFactory;

import java.io.InputStream;
import java.util.List;

/**
 * Standalone XML application context, taking the context definition files
 * from the class path, interpreting plain paths as class path resource names
 * that include the package path (e.g. "mypackage/myresource.txt"). Useful for
 * test harnesses as well as for application contexts embedded within JARs.
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文
     *
     * @param configLocations
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
        this(new String[]{configLocations});
    }

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文
     * @param configLocations
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;

        refresh();
    }
    public ConfigurableListableBeanFactory getConfigurableListableBeanFactory(){
        System.out.println("返回一个BeanFactory  类型是 ：" +getBeanFactory().getClass().getName());
        return getBeanFactory();
    }
    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

    @Override
    public <T> List<T> getBeansByType(Class type) {
        return null;
    }

    @Override
    public InputStream getResource() {
        return null;
    }



    @Override
    public void close() {

    }
}
