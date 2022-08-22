package com.lyt.AtianSpring.Aop.test;

import com.lyt.AtianSpring.Aop.aspectj.AspectJExpressionPointcut;
import com.lyt.AtianSpring.factory.test.UserServiceAop;
import org.junit.Test;

import java.lang.reflect.Method;

public class AopTest {
    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.lyt.AtianSpring.factory.test.UserServiceAop.*(..))");
        Class<UserServiceAop> clazz = UserServiceAop.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }
}
