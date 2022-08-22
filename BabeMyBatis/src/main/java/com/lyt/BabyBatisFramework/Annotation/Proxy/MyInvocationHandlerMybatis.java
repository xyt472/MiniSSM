package com.lyt.BabyBatisFramework.Annotation.Proxy;

import com.lyt.BabyBatisFramework.Annotation.*;
import com.lyt.BabyBatisFramework.Annotation.Param;
import com.lyt.BabyBatisFramework.SqlSession.SqlSession;
import com.lyt.BabyBatisFramework.Utils.BaseDaoPlus;
import com.lyt.BabyBatisFramework.Utils.objectToMap;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;


public class MyInvocationHandlerMybatis implements InvocationHandler {

    private Object object; //被代理的对象
    //这里很关键  注入sqlsession
    private SqlSession sqlSession= BaseDaoPlus.sqlSessionFactory.openSession();

    public  MyInvocationHandlerMybatis(Object object){
        this.object=object;
    }
    /**
     *
     * @param proxy  被代理对象
     * @param method 被拦截的方法
     * @param args  方法上的参数值
     * @return
     * @throws Throwable
     */
    @Override  //必须要重写
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statementId=  AnnotationValue(getAnnotation(method));  //
//        System.out.println(" method.invoke(args) 的执行结果 ："+ method.invoke(object.getClass().getDeclaredConstructor().newInstance(),args));
        Annotation annotation=getAnnotation(method);
        if(annotation instanceof Select){
           return callSelect(annotation,method,args);
    }else {
            //获取注解中的value  即statementId语句
            int n= sqlSession.update(statementId,param(method,args));
            return  n;
        }
    }


    public Object callSelect(Annotation annotation, Method method, Object[] args)throws Exception{
        String statementId=  AnnotationValue(getAnnotation(method));  //
        if(((Select) annotation).type().equals("selectOne")){
                return sqlSession.selectOne(statementId,param(method,args));
            }if(((Select) annotation).type().equals("selectList")){

                return  sqlSession.selectList(statementId,param(method,args));
            }if(((Select) annotation).type().equals("selectCount")) {
                return  sqlSession.selectCount(statementId,args);
            }
        return null;
        }

    //通过判断不同的注解类型来执行
    public Annotation getAnnotation(Method method){
        Annotation[] annotations=method.getDeclaredAnnotations();
            Annotation annotation=method.getDeclaredAnnotations()[0];
            if(annotation instanceof Insert){
                return annotation;
            }
            if(annotation instanceof Update){
                return annotation;
            }
            if(annotation instanceof Delete){
                return annotation;
            }
            if(annotation instanceof Select){
                return annotation;
            }
        return null;

    }

    //重构一个泛型函数用来获取对象类型
    public String AnnotationValue(Annotation annotation){
        if(annotation instanceof Insert){
            return ((Insert) annotation).value();
        }
        if(annotation instanceof Update){
            return ((Update) annotation).value();
        }
        if(annotation instanceof Delete){
            return ((Delete) annotation).value();
        }
        if(annotation instanceof Select){
            return ((Select) annotation).statementId();
        }
        return null;
    }

    //这里获取参数将其转换为map集合
    public Map<String,Object> param( Method method, Object[] args){
        Map<String,Object> param=new HashMap<>();

        if(args.length>1){

            if( !(args[0] instanceof Map))
            {
                //获取方法上的参数数组
                return processParam(param,method,args);
            }
        }else {
                if(args[0] instanceof Map){
                    param=(Map<String, Object>) args[0];
                    return param;
                }
                if(args[0] instanceof Integer || args[0] instanceof String){

                    return processParam(param,method,args);
                }
                else {
                    param = objectToMap.toMap(args[0]);
                    return param;
                }
            }

        System.out.println("map是空的---------------------------------------------");
        return null;
    }

    public Map<String,Object> processParam(Map param, Method method, Object[] args){
        Parameter[] parameters=method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter=parameters[i];
            Param annotationParam=parameter.getDeclaredAnnotation(Param.class);
            //判断一下是否有param注解
            if(annotationParam!=null){
                //参数的名称
                String paramName=annotationParam.value();
                Object value =args[i];  //参数对应的值
                System.out.println("paramName  :"+paramName+"  参数对应的值value ："+value);
                //将参数，value放入
                param.put(paramName,value);
            }
        }
        return param;
    }
}
