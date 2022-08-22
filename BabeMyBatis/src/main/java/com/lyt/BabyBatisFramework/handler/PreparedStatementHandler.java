package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.mapping.ResultMap;

import java.sql.*;
import java.util.List;

/**
 * 专门处理PreparedStatement对象的
 *
 */
public class PreparedStatementHandler implements StatementHandler {
    //todo 四大组件中的两大组件
    //todo  真正处理sql语句的函数
    private ParameterHandler parameterHandler;
    private ResultSetHandler resultSetHandler;
    //为了能够处理不同的参数 把真正的处理交给了 parameterHandler
    public PreparedStatementHandler(Configuration configuration) {
        parameterHandler=configuration.newParameterHandler();
        resultSetHandler=configuration.newResultSetHandler();

    }

    @Override
    public Statement prepare( String sql, Connection connection)throws Exception {
        return connection.prepareStatement(sql);
    }
    @Override
    public void parameterize(Object param, Statement statement, BoundSql boundSql)throws Exception  {

        if(statement instanceof PreparedStatement) {
            System.out.println("——+——+——+——+————+参数处理PreparedStatementHandler");
            //将statement统一转换为prpared类型
            PreparedStatement preparedStatement = (PreparedStatement) statement;//为什么要转换呢？
            // 因为这样就可以直接执行了
            //为了下面的能用
            parameterHandler.setParameters(param, preparedStatement, boundSql);  //设置参数

        }
    }
    @Override
    public List<Object> query(Statement statement, MappedStatement mappedStatement) {
        PreparedStatement preparedStatement = (PreparedStatement) statement;//为什么要转换呢？
        ResultSet rs= null;  //获得结果集
        try {
            rs = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            System.out.println("可能没有获取到结果");
            throwables.printStackTrace();
        }
        //todo 处理结果集
        List<Object> list= null;
        try {
            list = resultSetHandler.handleResultSet(preparedStatement,rs,mappedStatement);
        } catch (Exception e) {
            System.out.println("处理结果集有问题");
            e.printStackTrace();
        }
        return list;
    }
    //todo 开始执行嵌套映射
    @Override
    public List<Object> nestedQuery(Statement statement, MappedStatement mappedStatement, ResultMap resultMap){


        PreparedStatement preparedStatement = (PreparedStatement) statement;//为什么要转换呢？
        ResultSet rs= null;  //获得结果集
        try {
            rs = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            System.out.println("可能没有获取到结果");
            throwables.printStackTrace();
        }
        //todo 处理结果集
        List<Object> list= null;
        try {
            System.out.println("在PreparedStatementHandler类中  执行嵌套映射后获取数据");
            list = resultSetHandler.handleNestedResultSet(preparedStatement,rs,mappedStatement,resultMap);
        } catch (Exception e) {
            System.out.println("处理结果集有问题");
            e.printStackTrace();
        }
        return list;
    }

}
