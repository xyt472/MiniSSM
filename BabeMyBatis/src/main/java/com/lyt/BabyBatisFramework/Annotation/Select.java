package com.lyt.BabyBatisFramework.Annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)  //注解只能用在方法上
public @interface Select {
    String statementId();
    String type();
}
