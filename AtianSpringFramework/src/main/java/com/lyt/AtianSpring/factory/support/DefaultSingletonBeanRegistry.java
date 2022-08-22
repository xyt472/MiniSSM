package com.lyt.AtianSpring.factory.support;

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

        //System.out.println("getSingleton 想要获取的bean 的 名称 ：" +beanName);
        return this.singletonObjects.get(beanName);
    }

    Map<String,Object> getSingletonObjects(){
        return singletonObjects;
    }
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        if(beanName==null&&singletonObject!=null){
            beanName=singletonObject.getClass().getName();
        }
        if(beanName!=null&&singletonObject!=null){
         //   System.out.println("注册好的的单例bean名称 ："+beanName+"  类型"+singletonObject.getClass().getName());
            singletonObjects.put(beanName, singletonObject);
        }else {
            System.out.println("88888+++++++++++++++注册单例bean失败registerSingleton  beanname"+beanName);
        }
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
