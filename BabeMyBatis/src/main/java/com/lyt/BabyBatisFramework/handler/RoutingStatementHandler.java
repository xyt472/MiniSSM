package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.mapping.ResultMap;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * 通过路由跳转到不同的handler
 */
public class RoutingStatementHandler implements StatementHandler {
    private StatementHandler statementHandler;
    public RoutingStatementHandler(String statementType, Configuration configuration) {
        //可以使用Switch语句也可可以用 if
    if("prepared".equals(statementType)){
        statementHandler =new PreparedStatementHandler(configuration);
    }  //.....todo 还有很多要完成

    }
    @Override
    public Statement prepare(String sql, Connection connection) throws Exception {
        return statementHandler.prepare(sql,connection);
    }
    @Override
    public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception {
        return  statementHandler.query(statement,mappedStatement);
    }

    @Override
    public void parameterize(Object param, Statement statement, BoundSql boundSql) throws Exception {
        statementHandler.parameterize(param,statement,boundSql);
    }
    @Override
    public List<Object> nestedQuery(Statement statement, MappedStatement mappedStatement, ResultMap resultMap) throws Exception {
        return statementHandler.nestedQuery( statement,  mappedStatement, resultMap);
    }
}
