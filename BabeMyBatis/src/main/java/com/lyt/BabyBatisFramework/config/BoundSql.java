package com.lyt.BabyBatisFramework.config;

import java.util.List;

/**
 * 封装sql语句  和#{} 、${}解析出来的参数信息集合
 */
public class BoundSql {
    //已经解析完成的sql语句
    private  String sql;
    private List<ParameterMapping> parameterMappings;

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        System.out.println("这经过解析的sql语句 : "+sql);
        System.out.println("这经过解析的sql语句的parameterMappings 里面放着的是:预处理的数据？ "+parameterMappings);
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addParameterMappings(ParameterMapping parameterMappings) {

        this.parameterMappings.add(parameterMappings);
    }
}
