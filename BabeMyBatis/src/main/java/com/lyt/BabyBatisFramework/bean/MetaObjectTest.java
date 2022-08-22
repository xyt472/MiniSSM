package com.lyt.BabyBatisFramework.bean;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import java.util.HashMap;

public class MetaObjectTest {

    @Test
    public void metaTest(){
        /**
         * metaObject的功能有：
         * 直接操作属性
         * 操作子属性
         * 自动查找属性名，支持下划线转驼峰
         * 操作数组  数组是不能像操作对象那样会自动生成  得自己new一个
         *
         */
        //装饰器模式
        Configuration configuration=new Configuration();
        Object blog=Mock.newBlog(); //装饰器模式吗？
        MetaObject metaObject=configuration.newMetaObject(blog);  //装饰器模式的一种应用
        String propName=   metaObject.findProperty("author.id",true);  //开启驼峰命名法去寻找属性
        metaObject.setValue(propName,"12324");
        System.out.println( metaObject.getValue(propName));  //查值
        metaObject.setValue("body","srfgredgrte");
        metaObject.setValue("author.name","dddd");  //设置值得时候会自动创建这个对象
        System.out.println( metaObject.getValue("author.name"));
       // 数组是不能像操作对象那样会自动生成  得自己new一个
      //  metaObject.setValue("comments",new ArrayList<>());  //这样子也不行 得提前new好 newBlog的时候就已经new好了
        // Object blog=Mock.newBlog();
        //map类型也要new
        metaObject.setValue("labels",new HashMap<>());
        metaObject.setValue("labels[red]","地怒射");
        System.out.println(metaObject.getValue("labels[red]"));
        System.out.println(metaObject.getValue("comments[0].body"));
        System.out.println(metaObject.getValue("comments[0].user.name"));
        Object blog2=new Blog();
        MetaObject metaObject12=configuration.newMetaObject(blog2);
        //不能直接使用 list 前提是 new出来的对象里面有才行
//        metaObject12.setValue("comments",new ArrayList<>());
//        metaObject12.setValue("comments[0]","sdfsdf");
//        System.out.println("comments[0]:"+metaObject12.getValue("comments[0]"));

    }


}
