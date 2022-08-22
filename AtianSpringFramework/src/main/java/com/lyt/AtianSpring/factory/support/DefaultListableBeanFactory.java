package com.lyt.AtianSpring.factory.support;


import com.lyt.AtianSpring.config.BeanDefinition;
import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.factory.ConfigurableListableBeanFactory;
import com.lyt.AtianSpring.registry.BeanDefinitionRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 是spring中的真正管理Bean实例的容器工厂
 * 同时又是管理BeanDefinition的BeanDefinition注册器
 */
public class DefaultListableBeanFactory extends AbstractAutowiredCapableBeanFactory implements BeanDefinitionRegistry , ConfigurableListableBeanFactory {
    // 存储BeanDefinition的容器
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }
    @Override
    public void preInstantiateSingletons() throws BeansException {

        System.out.println("提前实例化所有 了所有单例类");
        beanDefinitions.keySet().forEach(this::getBean);
    }



    //所谓注册就是把BeanDefinition放入map中
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
            System.out.println("已经将beanDefinition注册到容器当中 beanName是："+beanName);
            this.beanDefinitions.put(beanName,bd);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        List<BeanDefinition> list = new ArrayList<>();
        for(BeanDefinition bd:beanDefinitions.values()){
            list.add(bd);
        }
        return list;
    }


    //类似一种扫描的作用？
    @Override
    public <T> List<T> getBeansByType(Class type) {
        List<T> results = new ArrayList<>();
        // 获取容器中所有的BeanDefinition，遍历每个BeanDefinition，取出来它的类型
        for(BeanDefinition bd:beanDefinitions.values()){
            Class<?> clazzType = bd.getClazzType();
            // type如果是clazzType的父类型，则返回true   父类型指的应该该类继承的接口
            /**
             * isAssignableFrom()
             * 确定一个类(B)是不是继承来自于另一个父类(A)，一个接口(A)是不是实现了另外一个接口(B)，
             * 或者两个类相同。主要，这里比较的维度不是实例对象，而是类本身，
             * 因为这个方法本身就是Class类的方法，判断的肯定是和类信息相关的。
             * 也就是判断当前的Class对象所表示的类，是不是参数中传递的Class对象所表示的类的父类，
             * 超接口，或者是相同的类型。是则返回true，否则返回false。
             */
            //与传进来的类类型进行类型匹配
            if (type.isAssignableFrom(clazzType)){
                results.add((T) getBean(bd.getBeanName()));
            }
        }
        return results;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
//        for(BeanDefinition beanDefinition:beanDefinitions.values()){
//            Class beanClass = beanDefinition.getClazzType(); //获取到加载的类的信息
//            if (type.isAssignableFrom(beanClass)) {
//                System.out.println("getBeansOfType中beanDefinition.getBeanName():"+beanDefinition.getBeanName());
//                result.put(beanDefinition.getBeanName(), (T) getBean(beanDefinition.getBeanName()));
//            }
//        }
//
//        for (BeanDefinition value : beanDefinitions.values()) {
//            System.out.println("-----++++++--------当前的bd name ："+value.getBeanName());
//            Object ob=getBean("defaultAdvisorAutoProxyCreator");
//            if(ob!=null){
//                System.out.println("获取 defaultAdvisorAutoProxyCreator 成功：");
//            }

 //       }
        beanDefinitions.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getClazzType(); //获取到加载的类的信息
            if (type.isAssignableFrom(beanClass)) {
               if(beanName!=null){
                   result.put(beanName, (T) getBean(beanName));
               }else {
                   System.out.println("+++------------+++++++++++++++++++++getBeansOfType 中 放入 失败");
               }
            }
        });
        return result;
    }
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitions.entrySet()) {
            Class beanClass = entry.getValue().getClazzType();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()) {

           // System.out.println("返回 的 bean 的类型是 "+getBean(beanNames.get(0), requiredType).getClass().getName());
            return getBean(beanNames.get(0), requiredType);
        }

        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitions.keySet().toArray(new String[0]);
    }




//    public void registerSingleton(String beanName, BeanDefinition singletonObject) {
//        if(singletonObject!=null){
//          if(beanName==null){
//              beanName=singletonObject.getClazzType().getName();
//          }
//            beanDefinitions.put(beanName, singletonObject);
//            System.out.println("---------singletonObject成功注册------------name是 ："+beanName+"  类型是 ："+singletonObject.getClazzType().getName());
//
//        }else {
//            System.out.println("singletonObject 是空的  无法注册----------------------------");
//        }
//    }




}
