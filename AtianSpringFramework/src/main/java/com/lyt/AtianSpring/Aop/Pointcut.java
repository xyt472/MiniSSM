package com.lyt.AtianSpring.Aop;

public interface Pointcut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
