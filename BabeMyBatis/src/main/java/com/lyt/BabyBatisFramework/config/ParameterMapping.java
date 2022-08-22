package com.lyt.BabyBatisFramework.config;


import lombok.Data;

/**
 * 这个类是用来封装 #{}解析出来的参数名称和参数类型
 */
@Data
public class ParameterMapping {
    private  String name;
    private  Class type;
    public  ParameterMapping(String name){
        this.name=name;
    }
}
