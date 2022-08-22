package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.BoundSql;

import java.sql.PreparedStatement;

/**
 *专门用来处理jdbc执行过程中参数的设置
 */
public interface ParameterHandler {

    public void setParameters(Object param, PreparedStatement preparedStatement, BoundSql boundSql) throws Exception;
}
