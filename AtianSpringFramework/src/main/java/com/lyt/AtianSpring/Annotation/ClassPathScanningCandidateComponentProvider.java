package com.lyt.AtianSpring.Annotation;

import cn.hutool.core.util.ClassUtil;
import com.lyt.AtianSpring.config.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {
    /**
     * 此方法唯一的参数是包的名称，返回结果为此包以及子包下所有的类。
     * 方法使用很简单，但是过程复杂一些，包扫面首先会调用 getClassPaths方法获得ClassPath，然后扫描ClassPath，
     * 如果是目录，扫描目录下的类文件，或者jar文件。如果是jar包，则直接从jar包中获取类名。
     * 这个方法的作用显而易见，就是要找出所有的类，在Spring中用于依赖注入，我在Hulu中则用于找到Action类。当然，
     * 你也可以传一个ClassFilter对象，用于过滤不需要的类。
     * 这里先要提供一个可以通过配置路径
     * basePackage=cn.bugstack.springframework.test.bean，解析出
     * classes 信息的工具方法 findCandidateComponents，通过这个方法就可以扫描到
     * 所有 @Component 注解的 Bean 对象了。
     * @param basePackage
     * @return
     */
    //todo  扫描包里面的注解  注解的扫描都在这里    这个类声明时候会被调用？ 在 加载xml文件的时候
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        Set<Class<?>> classes2= ClassUtil.scanPackageByAnnotation(basePackage,DAO.class);
        Set<Class<?>> classes3= ClassUtil.scanPackageByAnnotation(basePackage, Controller.class);
        Set<Class<?>> classes4= ClassUtil.scanPackageByAnnotation(basePackage, Service.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        for (Class<?> aClass : classes2) {
            candidates.add(new BeanDefinition(aClass));
        }
        for (Class<?> aClass : classes3) {
            System.out.println("<><><><><><><>扫描到并注册Controller");
            candidates.add(new BeanDefinition(aClass));
        }
        for (Class<?> aClass : classes4) {
            System.out.println("<><><><><><><>扫描到并注册Service");
            candidates.add(new BeanDefinition(aClass));
        }
        return candidates;
    }
}
