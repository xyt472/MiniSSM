package com.lyt;

import com.lyt.AtianSpringMvc.annotation.RequestMapping;
import com.lyt.controller.UserController;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
@Data
public class controllerTest {
    UserController  userController=new UserController();
    Map<String, Method> methodMap=new HashMap<>();
   
    public void  test(){
        //获取 类
        Class clazz=userController.getClass();


        //获取方法
        Method []methods =clazz.getMethods();
        
        //吧方法丢进map集合里面   key是 requestMappingd的值  value是 method

        for (Method method : methods) {
           // Class methodClass=method.getClass();
            //放进集合当中
          String uri= method.getAnnotation(RequestMapping.class).value();
            methodMap.put(uri,method);
        }
        
        //根据前端发过来到地请求来分发
        
        
    }
}
