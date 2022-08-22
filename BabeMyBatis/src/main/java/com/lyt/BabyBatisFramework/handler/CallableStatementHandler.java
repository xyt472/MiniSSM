package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.mapping.ResultMap;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class CallableStatementHandler implements StatementHandler{
    @Override
    public Statement prepare(String sql, Connection connection) throws Exception {
        return null;
    }

    @Override
    public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception {
        return null;
    }
    @Override
    public List<Object> nestedQuery(Statement statement, MappedStatement mappedStatement, ResultMap resultMap) throws Exception {
        return null;
    }
    @Override
    public void parameterize(Object param, Statement statement, BoundSql boundSql) throws Exception {

    }
}
