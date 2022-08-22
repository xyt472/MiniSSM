package com.lyt.AtianSpring.factory.test;

import com.lyt.AtianSpring.factory.support.DefaultListableBeanFactory;
import com.lyt.AtianSpring.reader.XmlBeanDefinitionReader;
import com.lyt.AtianSpring.resource.ClasspathResource;
import com.lyt.AtianSpring.resource.Resource;
import org.junit.Test;

import java.io.InputStream;

public class test {
    private DefaultListableBeanFactory beanFactory ;
    @Test
    public void testScan(){
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
//        UserService userService = applicationContext.getBean("userService", UserService.class);
//        System.out.println("测试结果：" + userService.queryUserInfo());
        // 提前加载spring容器需要的BeanDefinition信息
        beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClasspathResource("springmvc.xml");
        InputStream inputStream = resource.getResource();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

       // ApplicationContext applicationContext=new ClassPathXmlApplicationContext("springmvc.xml");

        beanDefinitionReader.loadBeanDefinitions(inputStream);
      //  System.out.println(beanFactory.getBeanDefinitionNames()[1].toString());
        // 将spring容器中管理的所有单例Bean进行初始化(getBean)   先加载的servlet 再装配的bean
       // beanFactory.getBeansByType(Object.class);  //目前为止还是无法实现 自动装配  这写的不好
       UserService userService=(UserService) beanFactory.getBean("userService");
        System.out.println(userService.queryUserInfo());
       //userService.queryUserInfo();
    }
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor(){
        beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClasspathResource("springmvc.xml");
        InputStream inputStream = resource.getResource();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(inputStream);
//        // ApplicationContext applicationContext=new ClassPathXmlApplicationContext("springmvc.xml");
//
//        beanDefinitionReader.loadBeanDefinitions(inputStream);

        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
     //   System.out.println("    获得的    beanFactory.getBeanDefinitionNames()的长度"+beanFactory.getBeanDefinitionNames());
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
// 4. Bean 实例化之后，修改 Bean 属性信息
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);
// 5. 获取 Bean 对象调用方法
       // UserService userService= (UserService) beanFactory.getBean("userService");
        UserService userService = beanFactory.getBean("userService", UserService.class) ;
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }
}
