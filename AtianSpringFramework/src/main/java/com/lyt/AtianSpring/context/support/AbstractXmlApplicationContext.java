package com.lyt.AtianSpring.context.support;


import com.lyt.AtianSpring.factory.support.DefaultListableBeanFactory;
import com.lyt.AtianSpring.reader.XmlBeanDefinitionReader;
import com.lyt.AtianSpring.resource.ClasspathResource;
import com.lyt.AtianSpring.resource.Resource;

import java.io.InputStream;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {



    //todo 这里有待修改！！！！！！！！！！因为吧这里写死了
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] configLocations = getConfigLocations();
        System.out.println("ClassPathXmlApplicationContext中开始  加载bBeanDefinition");
        System.out.println("configLocations  中寸的长度是 ："+configLocations.length);
        //todo 这里需要修改  一下吧  使他能够 加载多个路径
        Resource resource = new ClasspathResource("springmvc.xml");
        InputStream inputStream = resource.getResource();
        if (null != configLocations){
            if(inputStream!=null){
                System.out.println("inputStream 不空的");
                beanDefinitionReader.loadBeanDefinitions(inputStream);
            }else {
                System.out.println("inputStream 是空的");
            }

        }
    }

    protected abstract String[] getConfigLocations();

}
