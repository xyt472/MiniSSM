package com.lyt.BabyBatisFramework.SqlSource;

import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.ParameterMapping;

import java.util.List;

/**
 *
 *
 */
public class StaticSqlSource implements SqlSource{
    //一个静态的sqlSource（StaticSqlSource）

    private String sql;
    private List <ParameterMapping> parameterMappings;
    public  StaticSqlSource(String sql,List <ParameterMapping> parameterMappings){
        this.sql=sql;
        this.parameterMappings=parameterMappings;

    }

    //这是处理流程
    @Override
    public BoundSql getBoundSql(Object param) {
        return new BoundSql(sql,parameterMappings);
    }

}
