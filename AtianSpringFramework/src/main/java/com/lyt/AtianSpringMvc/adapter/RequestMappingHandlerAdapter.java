package com.lyt.AtianSpringMvc.adapter;


import com.alibaba.fastjson.JSON;
import com.lyt.AtianSpring.Annotation.Component;
import com.lyt.AtianSpringMvc.annotation.RequestBody;
import com.lyt.AtianSpringMvc.annotation.ResponseBody;
import com.lyt.AtianSpringMvc.model.HandlerMethod;
import com.lyt.AtianSpringMvc.model.ModelAndView;
import com.lyt.AtianSpringMvc.utils.Json.AcceptJsonUtils;
import com.lyt.AtianSpringMvc.utils.JsonUtils;

import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;


import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RequestMappingHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supports(Object handler) {
        //如果是controller类型的话
        return (handler instanceof HandlerMethod);
    }

    //最最重要的核心功能  执行handler 返回ModelAndView（这里只是用response写入了json）
    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //会传进来 一个 handler即 controller
        HandlerMethod hm = (HandlerMethod) handler;
        Object controller = hm.getController();      //有controller注解的类？ 还是那个有RequestMapping的函数？应该是后者
        Method method = hm.getMethod();
        // 处理请求参数

        Object args1[] = new Object[1];

        Object args[];
        //这里设计的有问题！！！！！！！！！！！！！！！
        if(method.getParameters().length==1&&method.getParameters()[0].getName().equals("request")){
            args1[0]=request;
            args=args1;
        }else {
            args = handleParameters(request,method);  //万一他不携带参数呢？

        }

        // todo 执行controller
        // 传入参数 并且通过反射调用方法    参数类型和数量都要要匹配被invoke的方法
        if(controller==null){
            System.out.println("<><><><><><><>controller 为空  ");
        }else {
            System.out.println("<><><><><><><>controller不 为空  类型是 ："+controller.getClass().getName());
        }
        Object returnValue=null;
        System.out.println("_+_++_+_+_+_+_InvocationTargetException  的controller是 ："+controller.getClass().getName());
        System.out.println("_+_++_+_+_+_+_InvocationTargetException  的method是 ："+method.getName());

        //执行 handler
        System.out.println("args参数是 "+args.toString());
        returnValue = method.invoke(controller, args);  //这里会有返回值 可能返回的是对象  绝不能为空

        //这时候已经完成了servlet的调用 可以 设置 响应头了

        System.out.println("得到返回值 "+returnValue);


        // 处理返回值
        //这里已经开始写入json数据了  可能返回的只是string 但也可能返回的是对象 是对象的话就将其转换为json
        handleReturnValue(returnValue,response,method);

        return null;
    }


    //todo 有待修改 需要增加一个 接受json数据的功能
    private Object[] handleParameters(HttpServletRequest request, Method method) {
        List<Object> args = new ArrayList<>();
        // 请求参数KV集合
        Map<String, String> parameterMap = request.getParameterMap(); //一次性设置好参数
        if(parameterMap!=null){
            System.out.println("获取参数集合request.getParameterMap()"+request.getParameterMap().toString());
            for (String value : parameterMap.values()) {
                System.out.println("获取的参数值是 ；"+value);
            }
        }else {
            System.out.println("获取的参数集合为空");
        }
        // 方法参数数组
        Parameter[] parameters = method.getParameters();

        Object vo=null;
        for (Parameter parameter : parameters) {
            // 取出参数名称（需要特殊处理，否则获取到的就是arg0,arg1这样的参数名称）
            
            if(parameter.getAnnotation(RequestBody.class)!=null){
                //获取该参数上有该注解 则将其转换为json
                Class clazz=parameter.getType(); //获取参数的类型
                try {
                    String name = parameter.getName();
                    //将json数据转换为对象
                  vo= AcceptJsonUtils.getJson(clazz,request);
                  //放入参数集合当中
                    if (vo!=null){
                        System.out.println("接收到的参数vo："+vo.toString());
                    }else {
                        System.out.println("vo 是空的");
                    }

                    args.add(vo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }if(parameterMap!=null&&(parameter.getAnnotation(RequestBody.class))==null) {
                String name = parameter.getName();
                Class<?> type = parameter.getType();
                System.out.println("参数的name是:"+name);
                // 方法参数名称一定要和请求的Key保持一致
                String strings = parameterMap.get(name);
                Object valueToUse = resolveValue(strings,type); //将请求中携带的数据转换为我们想要的
                args.add(valueToUse);
            }

        }
        //为什么要设置参数？ 因为他的参数完完全全就是来自 request
     //   System.out.println("参数的样子"+args.toArray());  //为什么得是array？ 因为args[]本来就是个数组
        return args.toArray();
    }

    //todo 这里的参数传递可能会存在一定的问题  应该使用注解传参
    private Object resolveValue(String strings, Class<?> type) {

        if (type == Integer.class){
            return Integer.parseInt(strings);
        }else if (type == String.class){
            System.out.println("strings[0]是个什么 ："+strings);
            return strings;
        }else {

        }
        return null;
    }

    //控制返回值的处理
    private void handleReturnValue(Object returnValue, HttpServletResponse response, Method method) throws Exception{
        //返回值一定是string类型


        System.out.println("_+_++_+_+_+进行返回值的处理");
        if (returnValue instanceof String){
            response.setContentLength(((String) returnValue).getBytes().length);
            response.setContentType("text/plain;charset=utf8");

            PrintWriter printWriter= response.getWriter();
            System.out.println("给前端返回 字符串 " +returnValue);
            printWriter.write(response.genProtocol()+returnValue);
            printWriter.close(); //写入socket   如果你不写 close 那么数据是发送不到前端的
        }
        if (method.getAnnotation(ResponseBody.class) !=null){
            System.out.println("返回json数据 ");
            String data=JSON.toJSONString(returnValue);
            response.setContentLength(data.getBytes().length);
           // response.setContentLength(JsonUtils.object2Json(returnValue).getBytes().length);
            response.setContentType("application/json;charset=utf8");
            PrintWriter printWriter= response.getWriter();
           // printWriter.write(response.genProtocol()+JsonUtils.object2Json(returnValue));
            printWriter.write(response.genProtocol()+data);
            printWriter.close(); //写入socket
        }
       //
    }
}
