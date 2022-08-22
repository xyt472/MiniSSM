package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.mapping.ResultMap;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {
    public Statement prepare(String sql, Connection connection) throws Exception;

    public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception;
    public List<Object> nestedQuery(Statement statement, MappedStatement mappedStatement, ResultMap resultMap) throws Exception;

    void parameterize(Object param, Statement statement, BoundSql boundSql) throws Exception;
}
