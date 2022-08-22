package com.lyt.AtianSpringMvc.mapping;


import com.lyt.AtianSpring.Annotation.Component;
import com.lyt.AtianSpring.factory.BeanFactoryAware;
import com.lyt.AtianSpring.Annotation.Controller;
import com.lyt.AtianSpringMvc.annotation.RequestMapping;
import com.lyt.AtianSpringMvc.model.HandlerMethod;
import com.lyt.AtianSpring.config.BeanDefinition;
import com.lyt.AtianSpring.factory.BeanFactory;
import com.lyt.AtianSpring.factory.support.DefaultListableBeanFactory;
import com.lyt.AtianSpring.init.InitializingBean;
import com.lyt.servlet.HttpServletRequest;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestMappingHandlerMapping implements HandlerMapping, InitializingBean, BeanFactoryAware {
    //这里面保存的是 uri 和 controller 下的方法 之间的映射
   private Map<String, HandlerMethod> urlHandlers = new HashMap<>();

    //注入beanFactry这样才可以实现 spring整合springMvc
    private DefaultListableBeanFactory beanFactory;
    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();  //根据uri路径获取到对应的请求
        return this.urlHandlers.get(uri);
    }



    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    //生成RequestMappingHandlerMapping之后  对HandlerMethod进行修饰后放入集合当中里面放着的是各个集合的处理方法
    @Override
    public void afterPropertiesSet() {
        System.out.println("_+_+_+_+_+_+开始执行afterPropertiesSet");
        // 获取到所有的 类定义
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
//        for (BeanDefinition beanDefinition : beanDefinitions) {
//           // System.out.println( "输出当前的 beanfactroy 的名称getBeanName ："+beanDefinition.getBeanName());
//           // System.out.println( "输出当前的 beanfactroy 的 beanDefinition 的类名称 ："+beanDefinition.getClazzType());
//        }
        //找到你装配的controller  然后把它们的每一个方法 设置好每一个handler的方法路径 该路径作为key
        //方法作为value 放入 handler集合中
    //    System.out.println("开始遍历所有的bd  看看他们是不是 带有 Controller或者 RequestMapping   ");
        for (BeanDefinition bd : beanDefinitions) {
           // System.out.println("查看当前 beanDefinitions中的 className "+bd.getClazzType());
            Class<?> clazzType = bd.getClazzType();
            //  判断当前 类  是否是 controller  如果 类上带有@Controller或者@RequestMapping注解
            if (isHandler(clazzType)){
              //  System.out.println("<><><><>><><><><><放进好hm 的只能是 controller类 不信你看 ：" +clazzType.getName());
              //  System.out.println("开始 获取 Controller中的方法");
                RequestMapping classMapping = clazzType.getAnnotation(RequestMapping.class);
                String classUrl = classMapping.value();  //类注解上的路径
                Method[] methods = clazzType.getDeclaredMethods();  //获取controller类下所有的方法
                int i=0;
                for (Method method : methods) {
                    //判断该方法上有无这个注解   有这个注解的话再追加
                    if (method.isAnnotationPresent(RequestMapping.class)){
                        //建立URL和HandlerMethod对象的映射关系
                        // 获取URL
                        StringBuffer sb = new StringBuffer();
                        if (classUrl != null){
                            if (!classUrl.startsWith("/")){
                                sb.append("/");
                            }
                            sb.append(classUrl);
                        }
                        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                        //获取RequestMapping上的value 即路径
                        String methodUrl = methodMapping.value();
                      //  System.out.println("正在获取发送 到的uri :"+methodUrl);
                        //todo 这里还可以增加一些逻辑 提高容错率
                        if (!methodUrl.startsWith("/")){
                            //如果没有/的话就加上  这样可以提高容错率
                            sb.append("/");
                        }
                        sb.append(methodUrl);
                        // 获取HandlerMethod   通过类名获取到对应的 bean 和方法
                       // HandlerMethod hm = new HandlerMethod(beanFactory.getBean(bd.getBeanName()),method);
                        //获取到controller  是方法 来的
                        // todo 这里有待优化  按照类型去获取类 不如使用 getbean（name） 高效
                      //  System.out.println("《》《》afterPropertiesSet 中的 bd name    "+ bd.getBeanName());


                        //在这里将controll实例化  并且自动注入填充属性
                            HandlerMethod hm = new HandlerMethod(beanFactory.getBean(bd.getClazzType()),method);
                            // 放入urlHandlers中  这样才可以交给 视图解释器去处理啊
                            this.urlHandlers.put(sb.toString(),hm);
//                          System.out.println("urlHandlers中 的uri是 "+sb.toString());
//                            System.out.println("将handler放入 urlHandlers中 第"+(i++)+"次");


                    }
                }
            }
        }
    }
    private boolean isHandler(Class clazzType){
        return (clazzType.isAnnotationPresent(Controller.class)
                || clazzType.isAnnotationPresent(RequestMapping.class));
    }

}
