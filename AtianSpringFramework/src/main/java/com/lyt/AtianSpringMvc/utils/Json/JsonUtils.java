package com.lyt.AtianSpringMvc.utils.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonUtils {

//    public static Object JsonToPojo(String json,Object Obj){
//        Object jsonString = JSON.toJSON(Person.getPerson());
//        String jsonString2 =jsonString.toString();
//        Obj = JSON.parseObject(jsonString2, Object.class); //第二个参数指明要转换的对象
//        return Obj ;
//    }
//    @Test
//    public void JsonToPojoTest(){
//        Person person=Person.getPerson();
//        String jsonString = "{\"name\":\"张三\",\"sex\":\"男\",\"age\":25}";
//        Object obj=JsonToPojo(jsonString, person);
//        System.out.println("json对象转javaBean"+obj.toString());
//
//    }
//这方法没啥用
    public static Object pojoToJson(Object pojo){
        //Person person = new Person("张三", "男", 25);
        Object jsonString = JSON.toJSON(pojo);
        //System.out.println("这是fastjson方式生成的json字符串： "+jsonString.toString());
        return  jsonString;
    }

    //返回json就用这个方法就行
    public static void outJsonString(Object pojo,HttpServletResponse resp){
        //Person person = new Person("张三", "男", 25);
        String jsonString = JSON.toJSON(pojo).toString();
        //System.out.println("这是fastjson方式生成的json字符串： "+jsonString.toString());
        try {
            PrintWriter out =resp.getWriter();
            System.out.println("outJsonString:  "+jsonString);
            out.write(jsonString);//向客户端返回
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
//    @Test
//    public  void pojoToJsonTest(){
//        Person person=Person.getPerson();
//        Object obj=pojoToJson(person);
//        System.out.println("pojo转换json字符串"+obj.toString());
//    }


    //这怎么测试呢？？ 该测试已经写在了Servletzg中 可以实现对象 和数组 输出 前提是你的 对象里面有数组
    public static  void responseArrayJson(Object obj,HttpServletResponse resp)throws  Exception{


        String jsonString=JSON.toJSONString(obj);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(jsonString);  //这样子确实返回了json数据

    }
    //将对象转换为json发送给前端
    public static  void responseJsonMap(Object obj,HttpServletResponse resp,Map<String,String> map){
        JSONObject jsonObject= (JSONObject) JSONObject.toJSON(obj);
        jsonObject.put("Map",map);
        resp.setContentType("text/html;charset=utf-8");
        try {
            resp.getWriter().write(jsonObject.toJSONString());  //这样子确实返回了json数据
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
