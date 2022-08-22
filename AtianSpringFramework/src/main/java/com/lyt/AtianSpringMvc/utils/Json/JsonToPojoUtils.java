package com.lyt.AtianSpringMvc.utils.Json;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class JsonToPojoUtils {

    public static <T> Object ToPojo(String json,Class<T> obj){
        //obj必须是指明的类
        Object yourObj = JSON.parseObject(json,obj); //第二个参数指明要转换的对象  他本身就是一个泛型类了
        System.out.println("转化后的youObject"+yourObj.toString());
    return yourObj ;
    }
 //   @Test   //1
//    public  void ToPojoTest(){
//
//        String jsonString = "{\n" +
//                "    \"password\":\"123456\",\n" +
//                "    \"userName\":\"admin\"\n" +
//                "}";
//        // Person p=(Person) ToPojo(jsonString,(Object)Person.getPerson());
//        Admin ad=(Admin)ToPojo(jsonString,Admin.class);
//
//        System.out.println(ad.toString());
//        //System.out.println(ad.getPassword()+ad.getUseName());
//    }
}
