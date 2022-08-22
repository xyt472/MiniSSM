package com.lyt.BabyBatisFramework.executor;

import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;

import java.util.List;

/**
 * 用来执行jdbc 逻辑
 */
public interface Executor {
    <T>List <T> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

    int queryCount(Configuration configuration, MappedStatement mappedStatement, Object param)throws Exception;
     int update(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;
}
