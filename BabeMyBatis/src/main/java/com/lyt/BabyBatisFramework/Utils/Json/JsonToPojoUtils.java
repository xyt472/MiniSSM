package com.lyt.BabyBatisFramework.Utils.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import org.junit.Test;

public class JsonToPojoUtils {

    public static <T> Object ToPojo(String json,Class<T> obj){
        //obj必须是指明的类
        Object yourObj = JSON.parseObject(json,obj); //第二个参数指明要转换的对象  他本身就是一个泛型类了
        System.out.println("转化后的youObject"+yourObj.toString());
    return yourObj ;
    }

}
