package com.lyt.AtianSpring.factory.support;


import com.lyt.AtianSpring.config.*;
import com.lyt.AtianSpring.core.ConversionService;
import com.lyt.AtianSpring.factory.FactoryBean;
import com.lyt.AtianSpring.utils.ClassUtils;
import com.lyt.AtianSpring.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象类AbstractBeanFactory主要是完成getBean操作的共性部分的实现
 * 将特性部分的实现，让子类去完成（抽象模板方法）
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport  implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private ConversionService conversionService;
    //这里说明下，embeddedValueResolvers 是 AbstractBeanFactory 类新增加的集合
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
       // System.out.println("。。。。。。。。。调用的是 AbstractBeanFactory中的getbean  name：" +name);
        return (T) getBean(name);
    }
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }
//    @Override
//    public Object getBean(String beanName){
//        // 1.首先从singletonObjects集合中获取对应beanName的实例
//        Object singletonObject = getSingleton(beanName);
//        // 2.如果有对象，则直接返回
//        if (singletonObject != null){
//            return singletonObject;
//        }
//        System.out.println("beanname:"+beanName);
//        // 3.如果没有改对象，则获取对应的BeanDefinition信息
//        BeanDefinition beanDefinition = getBeanDefinition(beanName);
//        if (beanDefinition == null) {
//            System.out.println("beanname:"+beanName);
//            System.out.println("在getbean()中  beanDefinition为空");
//            return null;
//        }
//        // 4.判断是单例还是多例，如果是单例，则走单例创建Bean流程
//        if (beanDefinition.isSingleton()){
//            singletonObject = createBean(beanDefinition,null) ;
//
//            addSingleton(beanName,singletonObject);
//        }else if(beanDefinition.isPrototype()){
//            System.out.println(" beanDefinition.isPrototype==========================");
//            singletonObject = createBean(beanDefinition,null) ;
//        }
//        // 5.单例流程中，需要将创建出来的Bean放入singletonObjects集合中
//        // 6.如果是多例，走多例的创建Bean流程
//
//        return singletonObject;
//    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
         //   System.out.println("999999---------------------已经从sharedInstance中获取到bean ：------- "+sharedInstance.getClass().getName());
//            for (String s : getSingletonObjects().keySet()) {
//                System.out.println("《》《》《》《所有的singleton 的  key 名称是 "+s);
//            }
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);



        Object bean=null;
        if(beanDefinition!=null){
           // System.out.println("333333--------成功获取到beanDefinition 现在根据beanDefinition来实例化对象");
            bean   = createBean(beanDefinition, args);
        }else {
            System.out.println("！！！！！！！！！-----获取beanDefinition失败----！！！！！");
        }
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    // 需要延迟到DefaultListableBeanFactory去实现
    protected abstract BeanDefinition getBeanDefinition(String beanName);
    protected abstract Object createBean(BeanDefinition bd,Object[] args);
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
       // System.out.println("添加了beanPostProcessor名字是"+beanPostProcessor.getClass().getName());
        //移除集合中出现的第一个和指定元素匹配(存在的话)的元素(并返回true)
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }
    // 需要延迟到AbstractAutowiredCapableBeanFactory去实现
    public List<BeanPostProcessor> getBeanPostProcessorS(){
        return this.beanPostProcessors;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }
    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }
}
