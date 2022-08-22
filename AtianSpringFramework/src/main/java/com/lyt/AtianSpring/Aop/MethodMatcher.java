package com.lyt.AtianSpring.Aop;

import java.lang.reflect.Method;

/**
 * 定义 匹配类，用于切点找到给定的接口和目标类
 * 方法匹配，找到表达式范围内匹配下的目标类和方法。
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);

}
