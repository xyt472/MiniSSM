package com.lyt.AtianSpring.factory.support;


import com.lyt.AtianSpring.config.BeanDefinition;
import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.factory.InstantiationStrategy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    /**
     * 首先通过 beanDefinition 获取 Class 信息，这个 Class 信息是在 Bean 定义的时
     * 候传递进去的。
     *  接下来判断 ctor 是否为空，如果为空则是无构造函数实例化，否则就是需要有构
     * 造函数的实例化。
     *  这里我们重点关注有构造函数的实例化，实例化方式为
     * clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);，
     * 把入参信息传递给 newInstance 进行实例化。
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getClazzType());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == ctor) return enhancer.create();
        return enhancer.create(ctor.getParameterTypes(), args);
    }

}
