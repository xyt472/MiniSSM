package com.lyt.AtianSpring.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static Object createBean(Class clazz){
        try {
            // 选择无参构造器
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void invokeMethod(Object bean, String initMethod) {
        try {
            Class<?> clazz = bean.getClass();
            Method method = clazz.getDeclaredMethod(initMethod);
            method.setAccessible(true);
            //todo 这里有问题吧？都没有对bean进行处理
            //没有问题 这是对方法的调用
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setProperty(Object bean,String name,Object valueToUse){
        try {
            Class<?> aClass = bean.getClass();
            Field field = aClass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean,valueToUse);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Class<?> getTypeByFieldName(String beanClassName, String name) {
        try {
            Class<?> clazz = Class.forName(beanClassName);
            Field field = clazz.getDeclaredField(name);
            return field.getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Class resolveTargetType(Class<?> clazzType, String name) {
        try {
            Field field = clazzType.getDeclaredField(name);
            return field.getType();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Class resolveClassType(String clazzName) {
        try {
            return Class.forName(clazzName);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  null;

    }
}
