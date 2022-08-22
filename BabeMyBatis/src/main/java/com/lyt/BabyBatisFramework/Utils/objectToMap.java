package com.lyt.BabyBatisFramework.Utils;


import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;


import java.util.HashMap;
import java.util.Map;

public class objectToMap {


    public static Map<String,Object> toMap(Object param){
        /**1、传进来一个对象
         * 需要将他的属性名做为键
         * 属性的值作为valu
         *使用一个循环将他put进map中
         */
        Map<String,Object> params=new HashMap<>();
        Configuration configuration=new Configuration();

        MetaObject metaObject=configuration.newMetaObject(param); //丢进来的参数 就可以知道他是什么类
           String[] names=  metaObject.getGetterNames();   //获取所有的属性名称 将其放入一个string数组中
        for (String name : names) {
            //这里可能会有问题
            params.put(name,metaObject.getValue(name));
        }
        return params;
    }
}
