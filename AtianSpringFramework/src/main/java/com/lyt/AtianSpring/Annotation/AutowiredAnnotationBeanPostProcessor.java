package com.lyt.AtianSpring.Annotation;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.TypeUtil;
import com.lyt.AtianSpring.Aop.MapperProxy.MapperProxy;
import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.config.InstantiationAwareBeanPostProcessor;
import com.lyt.AtianSpring.config.PropertyValues;
import com.lyt.AtianSpring.core.ConversionService;
import com.lyt.AtianSpring.factory.BeanFactory;
import com.lyt.AtianSpring.factory.BeanFactoryAware;
import com.lyt.AtianSpring.factory.ConfigurableListableBeanFactory;
import com.lyt.AtianSpring.utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * AutowiredAnnotationBeanPostProcessor 是实现接口
 * InstantiationAwareBeanPostProcessor 的一个用于在 Bean 对象实例化完成后，设
 * 置属性操作前的处理属性信息的类和操作方法。只有实现了 BeanPostProcessor
 * 接口才有机会在 Bean 的生命周期中处理初始化信息
 *  核心方法 postProcessPropertyValues，主要用于处理类含有 @Value、
 * @Autowired 注解的属性，进行属性信息的提取和设置。
 * 这里需要注意一点因为我们在 AbstractAutowireCapableBeanFactory 类中使用的
 * 是 CglibSubclassingInstantiationStrategy 进行类的创建，所以在
 * AutowiredAnnotationBeanPostProcessor#postProcessPropertyValues 中需要判断是
 * 否为 CGlib 创建对象，否则是不能正确拿到类信息的。
 * ClassUtils.isCglibProxyClass(clazz) ?
 * clazz.getSuperclass() : clazz;
 */

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    //这个类会被感知到
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
    //这个是重点啊

    /**
     * 核心方法 postProcessPropertyValues，主要用于处理类含有 @Value、
     * @Autowired 注解的属性，进行属性信息的提取和设置。
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 1. 处理注解 @Value
        Class<?> clazz = bean.getClass();
        //判断是否是代理类吗》
//        if(ClassUtils.isCglibProxyClass(clazz) ){
//            System.out.println("<><><><><><>注入的这是cglib一个代理类");
//        }else {
//            System.out.println("<><><><><><>注入的不是一个代理类");
//        }
        //这是一种固定的写法吧？
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();  //类中的所有属性集合


        for (Field field : declaredFields) {

          Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                Object value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue((String) value);

                // 类型转换
                Class<?> sourceType = value.getClass();
                Class<?> targetType = (Class<?>) TypeUtil.getType(field);
                ConversionService conversionService = beanFactory.getConversionService();
                if (conversionService != null) {
                    if (conversionService.canConvert(sourceType, targetType)) {
                        value = conversionService.convert(value, targetType);
                    }
                }

                BeanUtil.setFieldValue(bean, field.getName(), value);

            }
        }

        // 2. 处理注解 @Autowired
        for (Field field : declaredFields) {
          Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                Class<?> fieldType = field.getType();
//                System.out.println("<<>><><><><><><>当前注解 下属性的 类型 :"+fieldType.getName() );
//                System.out.println("<<>><><><><><><>当前注解 下属性的 名称 :"+field.getName() );

                String dependentBeanName;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                // 根据名称 指定你要  注入的对象
                if (null != qualifierAnnotation) {
                    dependentBeanName = qualifierAnnotation.value();
                    //根据你注入的名称找到 这个类
                    dependentBean = beanFactory.getBean(dependentBeanName);
                } else {
                   // 如果没有指定名称 则根据 字段的类型 去找到这个类
                    dependentBeanName=field.getName();
                 //   System.out.println("<>><><>><>><>><>dependentBeanName是 ："+dependentBeanName+"+++++++++++++++++++++++++++++++++");
                    for (Annotation annotation : field.getType().getAnnotations()) {
                        if(annotation instanceof DAO){
                            //System.out.println("XXXXXXXXXXXXXX 这是dao 接口 现在要把他们交给 jdk去动态代理  ++++++++++++++++++++++++ ");
                            dependentBean= MapperProxy.getMapper(field.getType());
                          //  System.out.println("A-A-A-A-A-A-A-A-Ad-a-o-已经交由 jdk'去动态代理 ");
                        }else {
                            //这里应该是根据字段名去查找类
                            dependentBean = beanFactory.getBean(dependentBeanName);
//                            System.out.println("《》《》《》《》》《》当前的bean的类型是 ："+bean.getClass().getName());
//                            System.out.println("<><>><><><>>>><>>><>获取到的字段类型是 ："+field.getType());
//                            System.out.println("<><>><><><>>>><>>><>获取到的bean是 ："+dependentBean.getClass().getName());
                        }
                    }
                   // BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
                }
                //实例化待注入的对象  在遍历filed
                if(dependentBean==null){
                    dependentBeanName=field.getName();
                  //  System.out.println("dependentBean是空的  name是 "+dependentBeanName);
                    dependentBean=beanFactory.getBean(dependentBeanName);
                    if(dependentBean!=null){
                       // System.out.println("重新获取dependentBean   类型是 "+dependentBean.getClass().getName());
                    }else {
                     //   System.out.println("重新获取dependentBean 失败 ");
                    }
                }else {
                 //   System.out.println("dependentBean不为空");
                }
                field.setAccessible(true);
                try {
                    field.set(bean,dependentBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //  BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
              //  System.out.println("《》《》《》《》》《》setFieldValue 注入后 field的类型是 ："+field);

            }
        }
    //    System.out.println("《》《》《》《》》《》setFieldValue 注入后 bean的类型是 ："+bean.getClass().getName());

        return pvs;
    }



    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
