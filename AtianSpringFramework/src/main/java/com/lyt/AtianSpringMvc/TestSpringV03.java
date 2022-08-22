package com.lyt.AtianSpringMvc;


import com.lyt.AtianSpring.factory.support.DefaultListableBeanFactory;
import com.lyt.AtianSpring.reader.BeanDefinitionReader;
import com.lyt.AtianSpring.reader.XmlBeanDefinitionReader;
import org.junit.Before;
import org.junit.Test;

/**
 * 以面向过程思维去解决IoC的问题
 *
 */
public class TestSpringV03 {

    private DefaultListableBeanFactory beanFactory ;

    @Before
    public void before(){
        // BeanDefinition的注册
        // String location == "" ;

        beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = (BeanDefinitionReader) new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("beans.xml");

    }

    /**
     * 测试人员
     */
    @Test
    public void test(){
        // 注入对象
        beanFactory.getBean("userService");

        // 以下代码才是测试人员需要的代码
//        Map<String,Object> map = new HashMap<>();
//        map.put("username","123");
//        List<User> users = userService.queryUsers(map);
//        System.out.println(users);
    }

    // TODO spring的BeanFactory使用的是那种工厂方法？？？？
}
