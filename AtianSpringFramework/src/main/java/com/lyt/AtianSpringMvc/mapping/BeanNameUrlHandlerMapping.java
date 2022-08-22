package com.lyt.AtianSpringMvc.mapping;


import com.lyt.AtianSpring.config.BeanDefinition;
import com.lyt.AtianSpring.factory.BeanFactory;
import com.lyt.AtianSpring.factory.BeanFactoryAware;
import com.lyt.AtianSpring.factory.support.DefaultListableBeanFactory;
import com.lyt.AtianSpring.init.InitializingBean;
import com.lyt.servlet.HttpServletRequest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <bean name="/queryUser" class="QueryUserHandler的全路径"></bean>
 * （2）前端控制器把此请求转交给后端控制器，下面分析转交过程：当在spmvc-servlet.xml中
 * 查找能执行message.do请求的映射处理器时，发现没有能处理此请求的映射处理器，
 * 这时便使用默认的映射处理器BeanNameUrlHandlerMapping：
 * This is the default implementation used by the DispatcherServlet,
 * alongwith DefaultAnnotationHandlerMapping (on Java 5 and higher).
 * 我们还需注意：这种后端控制器的bean Name必须以“/”开头，并且要结合DispatcherServlet的映射配置
 *   <!-- 自定义处理器 -->
 *      <bean id="simpleController"
 *               name="/simpleController2.action"
 *                 class="com.wangBeat.module.simpleController"/>
 *
 *        <!-- 1、BeanNameUrlHandlerMapping 处理器映射器的 name 名字中必须要有 /
 *                                     （该映射器是使用处理器的 name 值来作为url映射的）
 *                             即根据 "http://loaclhost:8080/springMvc/simpleController2.action"
 *                          来找到处理器 com.wangBeat.module.simpleController -->

 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, InitializingBean, BeanFactoryAware {
    private Map<String,Object> urlHandlers = new HashMap<>();

    private DefaultListableBeanFactory beanFactory;

    public BeanNameUrlHandlerMapping() {
//        this.urlHandlers.put("/queryUser",new QueryUserHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return this.urlHandlers.get(uri);
    }

    @Override
    public void afterPropertiesSet() {
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            String beanName = bd.getBeanName();
            if (beanName.startsWith("/")){  //为什么要这样子标识呢？ 为了兼容一些
                this.urlHandlers.put(beanName,beanFactory.getBean(beanName));
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
