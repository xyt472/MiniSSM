package com.lyt.AtianSpring.registry;

import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.config.SingletonBeanRegistry;
import com.lyt.AtianSpring.factory.DisposableBean;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // 存储单例Bean实例的Map容器(线程安全的进行单例管理)  todo中点分析这个
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<>();
    protected static final Object NULL_OBJECT = new Object();
    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();
    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
//    @Override
//    public void addSingleton(String beanName, Object bean) {
//        // TODO 可以使用双重检查锁进行安全处理
//        System.out.println("放入addSingleton集合当中  beanName是："+beanName);
//        this.singletonObjects.put(beanName,bean);
//    }
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
