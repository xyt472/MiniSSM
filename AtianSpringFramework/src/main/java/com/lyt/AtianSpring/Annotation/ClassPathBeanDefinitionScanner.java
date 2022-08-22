package com.lyt.AtianSpring.Annotation;

import cn.hutool.core.util.StrUtil;
import com.lyt.AtianSpring.config.BeanDefinition;
import com.lyt.AtianSpring.registry.BeanDefinitionRegistry;

import java.util.Set;

/**
 * ClassPathBeanDefinitionScanner 是继承自
 * ClassPathScanningCandidateComponentProvider 的具体扫描包处理的类，在
 * doScan 中除了获取到扫描的类信息以后，还需要获取 Bean 的作用域和类名，如
 * 果不配置类名基本都是把首字母缩写。
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    //需要传进来一个registry
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
       // super();
       this.registry = registry;

    }


    //要扫描的包

    /**
     * 这里先要提供一个可以通过配置路径
     * basePackage=cn.bugstack.springframework.test.bean，解析出
     * classes 信息的工具方法 findCandidateComponents，通过这个方法就可以扫描到
     * 所有 @Component 注解的 Bean 对象了。
     * @param basePackages
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            //解析classes 信息的工具方法 findCandidateComponents，通过这个方法就可以扫描到comment注解
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析 Bean 的作用域 singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    System.out.println("正在设置作用域");
                    beanDefinition.setScope(beanScope);
                }
                //将BeanDefinition注册到容器当中
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
      //  System.out.println("注册处理注解的 BeanPostProcessor（@Autowired、@Value）");
        registry.registerBeanDefinition("com.lyt.AtianSpring.Annotation.AutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }
    //返回 作用域
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getClazzType();
        Scope scope = beanClass.getAnnotation(Scope.class);

        if (null != scope) return scope.value();
        return StrUtil.EMPTY;
    }

    //获取bean名称
    private String determineBeanName(BeanDefinition beanDefinition) {
        String value=null;
        Class<?> beanClass = beanDefinition.getClazzType();
        if(beanClass.getAnnotation(Component.class)!=null){
            Component component = beanClass.getAnnotation(Component.class);
             value = component.value();
        }
        if(beanClass.getAnnotation(DAO.class)!=null){
            DAO component = beanClass.getAnnotation(DAO.class);
            value = component.value();
        }
        if(beanClass.getAnnotation(Service.class)!=null){
            Service component = beanClass.getAnnotation(Service.class);
            Class<?> interfaces[]  =beanClass.getInterfaces();
            value = component.value();
            //如果他只实现了一个接口 并且 没有value 那么 注入的名称就是 接口的名称 首字母小写
            if(interfaces.length==1 &&StrUtil.isEmpty(value)){
                //如果你没有设置值得话  他会 帮你写上 首字母小写开头
                value = StrUtil.lowerFirst(interfaces[0].getSimpleName());

            }

        }
        if(beanClass.getAnnotation(Controller.class)!=null) {
            Controller component = beanClass.getAnnotation(Controller.class);
           // System.out.println("<><><><><><><>扫描到了controller");
            value = component.value();

        }

        if (StrUtil.isEmpty(value)) {
            //如果你没有设置值得话  他会 帮你写上 首字母小写开头
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
          //  System.out.println("扫描注解时  设置的 注册类名");
        }
        return value;
    }
}
