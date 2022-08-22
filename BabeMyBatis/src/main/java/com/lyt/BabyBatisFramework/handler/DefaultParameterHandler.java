package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.ParameterMapping;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DefaultParameterHandler  implements ParameterHandler{
    @Override
    public void setParameters(Object param, PreparedStatement preparedStatement, BoundSql boundSql) {
        /**
         * 设置参数 第一个参数为sql语句中的参数序号（从1开始），第二个参数开始为设置的
         * 如果入参是简单类型，那么我们不必关心参数的名称
         */
        System.out.println("   ——+——+——————+——+——参数是 ："+param);
        if (param instanceof Integer || param instanceof String) {
            try {
                preparedStatement.setObject(1, param);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (param instanceof Map) {
            System.out.println("   ——+——+——————+——+——参数的是 ："+param);

            Map<String, Object> map = (Map<String, Object>) param;  //参数应该已经改变了
            //TODO 下面这个需要解析#{}之后封装的List<ParameterMapping> 先暂时注释掉 实现后再来
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  //这个lisi暂时还没有实现
          //  System.out.println("parameterMappings设置参数"+param);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String name = parameterMapping.getName();
                Object value = map.get(name);
                try {
                    System.out.println("参数初始化执行"+i+"次");
                    preparedStatement.setObject(i + 1, value);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
            //todo  这里也要处理
        }else if(param instanceof Object){


        }
    }

}
