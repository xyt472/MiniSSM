package com.lyt.AtianSpringMvc.servlet;


import com.lyt.AtianSpring.context.support.ClassPathXmlApplicationContext;
import com.lyt.AtianSpringMvc.adapter.HandlerAdapter;
import com.lyt.AtianSpringMvc.mapping.HandlerMapping;
import com.lyt.AtianSpring.factory.support.DefaultListableBeanFactory;
import com.lyt.AtianSpring.reader.XmlBeanDefinitionReader;
import com.lyt.AtianSpring.resource.ClasspathResource;
import com.lyt.AtianSpring.resource.Resource;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;
import org.junit.Test;
import javax.servlet.ServletException;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * springmvc提供的唯一的一个Servlet类
 * DispatcherServlet 是前端控制器
 * ，Spring MVC 的所有请求都要经过 DispatcherServlet 来统一分发。DispatcherServlet
 * 相当于一个转发器或中央处理器，控制整个流程的执行，对各个组件进行统一调度，以降低组件之间的耦合性
 * ，有利于组件之间的拓展。
 */
public class DispatcherServlet extends AbstractServlet {

    /**
     * 流程 ：   doDispath->HandlerMapping->返回 handler （controller中的方法）------交给 adapter去执行
     * adapter 通过反射执行 controller 向前端 发送 json数据
     *
     */
    // HandlerAdapter的策略集合
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();
    // HandlerMapping的策略集合
    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private int initCount = 0;
    //手动注入beanFactory
    private DefaultListableBeanFactory beanFactory ;  //在初始化容器的时候 （initSpringContainer） 将其实例化

    //todo 当浏览器发来 get或者post请求时 会被调用  这种实现方式不好 有待改进
    @Override
    public void doDispath(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 根据请求，查找对应的处理类
            // 1.处理类长啥样？（它和Servlet无关，可以随便写，只是说为了统一，最后指定规范[接口]）
            // 2.去哪找处理类？（也就是请求URL和处理类的关系在哪建立）
            Object handler = getHandler(request); // handler指的是每一个controller里面的 方法
            if (handler == null){
                System.out.println("handler没有获取到");
                return ;
            }
            // 通过adapter 找到handler
            HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
            if (handlerAdapter == null) {
                System.out.println("handlerAdapter没有获取到");
                return ;
            }
            handlerAdapter.handleRequest(handler,request,response);  //执行的是  RequestMappingHandlerAdapter里面的方法
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Servlet的初始化

     * @throws ServletException
     */
    @Override
    public void init() {

        initSpringContainer("classpath:springmvc.xml");

        // 初始化策略集合
        initStrategies();
    }

    private void initStrategies() {
        //所谓的初始化 无非就是 从容器中 获得 所有的 所需要的bean对象 adapter
        //如果没有spring的话 就需要手动装配 非常麻烦
        initHandlerMappings();
        initHandlerAdapters();
    }


    private void initHandlerAdapters() {
        //放入适配器  理论上应该只有一个的吧
        // handlerAdapters = beanFactory.getBeansByType(HandlerAdapter.class);
        //从容器当中获取
         List  <HandlerAdapter> handlerAdapters2= new ArrayList();
        for (HandlerAdapter value : beanFactory.getBeansOfType(HandlerAdapter.class).values()) {
            //这里只注册了一个 就是 RequestMappingHandlerAdapter
            handlerAdapters2.add(value);
        }
        handlerAdapters=handlerAdapters2;
    }

    private void initHandlerMappings() {
        //放入映射器   RequestMappingHandlerMapping
       // handlerMappings = beanFactory.getBeansByType(HandlerMapping.class);
        List  <HandlerMapping> handlerMappings2= new ArrayList();
        //获取所有的 HandlerMapping
        for (HandlerMapping value : beanFactory.getBeansOfType(HandlerMapping.class).values()) {
            handlerMappings2.add(value);
        }
        handlerMappings=handlerMappings2;

    }

    @Test
    public void loadXml(){
        beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClasspathResource("springmvc.xml");
        InputStream inputStream = resource.getResource();
        if(inputStream!=null){
            System.out.println("成功读取xml");
        }
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(inputStream);
        // 将spring容器中管理的所有单例Bean进行初始化(getBean)   先加载的servlet 再装配的bean
        beanFactory.getBeansByType(Object.class);  //目前为止还是无法实现 自动装配  这写的不好
    }
    private void initSpringContainer(String location) {
//        // 提前加载spring容器需要的BeanDefinition信息
//        beanFactory = new DefaultListableBeanFactory();
//        Resource resource = new ClasspathResource(location);
//        InputStream inputStream = resource.getResource();
//        if(inputStream!=null){
//            System.out.println("成功读取xml");
//        }
//        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
//        beanDefinitionReader.loadBeanDefinitions(inputStream);
//        // 将spring容器中管理的所有单例Bean进行初始化(getBean)   先加载的servlet 再装配的bean
//        beanFactory.getBeansByType(Object.class);  //目前为止还是无法实现 自动装配  这写的不好
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        beanFactory=(DefaultListableBeanFactory) applicationContext.getConfigurableListableBeanFactory();
    }
    /**
     * 请求分发   这是mvc的核心
     * @throws ServletException
     * @throws IOException
     */


    @Override
    public Map<String, Object> getRequestAndResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }



    private HandlerAdapter getHandlerAdapter(Object handler) {
        if (handlerAdapters != null){
            // 遍历策略集合  什么叫策略模式？  定义一系列算法，把它们一个个封装起来，并且可以相互替换。
            for (HandlerAdapter ha : handlerAdapters) {
                //判断  如果是HandlerMethod（处理方法的话）
                //support  就是  用来判断策略的  决定你要使用的策略
                if (ha.supports(handler)){
                    return ha;
                }
            }
        }
        return null;
    }

    private Object getHandler(HttpServletRequest request) throws Exception{
        // 首先处理类和请求之间 的映射关系可能存储在不同的地方（HandlerMapping）
        //HandlerMapping 处理器映射器
        if (handlerMappings !=null){
            for (HandlerMapping hm : handlerMappings) {
                Object handler = hm.getHandler(request);
                if(handler != null){
                    return handler;
                }
            }
        }
        return null;
    }
}
