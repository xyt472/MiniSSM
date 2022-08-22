package com.lyt.AtianSpring.factory.test.CGLibTest;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class TestInterfaceCGLib implements MethodInterceptor {

    public Object getInstance(Class claxx) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(claxx);
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return method.getName()+"dfghsdgfhudsgfjhdsfg";
    }


    @Test
    public void  testProxy(){
        TestInterfaceCGLib testCGLib = new TestInterfaceCGLib();
        TestInterface o = (TestInterface) testCGLib.getInstance(TestInterface.class);
        System.out.println(o.getHalloWorld());


    }
}
