package com.lyt.BabyBatisFramework.executor;

import com.lyt.BabyBatisFramework.SqlSource.SqlSource;
import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.handler.StatementHandler;
import com.lyt.BabyBatisFramework.mapping.ResultMap;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是真正执行jdbc的代码 也是默认以这种方式去执行
 */
public class SimpleExecutor extends BaseExecutor{

//    protected List<Object> queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
//        List<Object> results = new ArrayList<>();
//        //这里可以优化一下吧？换成连接池
//        //Configuration configuration1=new Configuration();
//        Connection connection;
//        Statement statement=null; //为什么不用preparedStatement？  自己想想
//        ResultSet rs=null;
//      //  MappedStatement mappedStatement;
//        try {
//            // 连接的获取
//            connection=getConnection(configuration);
//            //todo sql的获取 (sqlSource 和SqlNode的处理流程) 这一块是比较困难的  得要两节课才能搞明白
//            //String sql=getSql(mappedStatement); //根据mapper的信息来获取到
//            SqlSource sqlSource =mappedStatement.getSqlSource();
//            //触发sqlSource和sqlNode的处理流程
//          //  BoundSql boundSql=sqlSource.getBoundSql(param);
//            String sql=boundSql.getSql();
//            System.out.println("从queryFromDataBase类中拿到最后处理好的sql语句"+sql);
//            //创建statement 执行句柄   这个很关键 需要将xml文件里的配置信息转换为 sql语句
//            //StatementHandler目的是为了解耦  用于处理statement操作 有三种（statement ,prepared,callable）
//            //通过statement可以屏蔽掉不同statement的处理逻辑  当然最主要的目的还是创建statement
//            StatementHandler statementHandler=configuration.newStatementHandler(mappedStatement.getStatementType());
//
//            statement =statementHandler.prepare(sql,connection);
//            //todo 设置参数
//            statementHandler. parameterize(param,statement,boundSql);
//            //执行statem
//           // rs= executeQuery(statement);
//            //处理结果  使用到了映射
//            //handlerResult(rs,mappedStatement,results);
//            //statementHandler直接处理后 就可以处理结果
//            System.out.println("正在执行");
//
//           results= statementHandler.query(statement,mappedStatement);
//           if(results!=null){
//               System.out.println("执行成功");
//           }else{
//               System.out.println("查询失败");
//           }
//        }catch (Exception e){
//            System.out.println("出现异常了");
//            e.printStackTrace();
//        } finally {
//            if(rs !=null){
//                try {
//                    rs.close();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            }
//            if(statement !=null){
//                try {
//                    statement.close();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            }
//        }
//
//        return  results; //返回结果集
//    }
@Override
protected List<Object> queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
    List<Object> results = new ArrayList<>();
    //Configuration configuration1=new Configuration();
    Connection connection;
    Statement statement=null; //为什么不用preparedStatement？  自己想想
    ResultSet rs=null;
    //  MappedStatement mappedStatement;
    try {
        // 连接的获取
        connection=getConnection(configuration);
        //todo sql的获取 (sqlSource 和SqlNode的处理流程) 这一块是比较困难的  得要两节课才能搞明白
        //String sql=getSql(mappedStatement); //根据mapper的信息来获取到
        SqlSource sqlSource =mappedStatement.getSqlSource();
        //触发sqlSource和sqlNode的处理流程
        // BoundSql boundSql2=sqlSource.getBoundSql(param);
        String sql=boundSql.getSql();

        System.out.println("从queryFromDataBase类中拿到最后处理好的但是并没有 设置参数sql语句"+sql);

        //创建statement 执行句柄   这个很关键 需要将xml文件里的配置信息转换为 sql语句
        //StatementHandler目的是为了解耦  用于处理statement操作 有三种（statement ,prepared,callable）
        //通过statement可以屏蔽掉不同statement的处理逻辑  当然最主要的目的还是创建statement
        StatementHandler statementHandler=configuration.newStatementHandler(mappedStatement.getStatementType());


        statement =statementHandler.prepare(sql,connection);  //预处理一下

        //todo 设置参数
        statementHandler. parameterize(param,statement,boundSql);
        //执行statem
        // rs= executeQuery(statement);
        //处理结果  使用到了映射
        //handlerResult(rs,mappedStatement,results);
        //statementHandler直接处理后 就可以处理结果
        System.out.println("正在执行");

        if(mappedStatement.getResultMap()!=null){
            System.out.println("《》《》《》《》《》正在执行的是SimpleExecutor类中的nestedQuery()");
            String resultMapId=mappedStatement.getResultMap();
            ResultMap resultMap=configuration.getResultMap(resultMapId);
            results = statementHandler.nestedQuery(statement,mappedStatement,resultMap);
            System.out.println("nestedQuery()获得的结果集是："+results);
        }
        else {
            results= statementHandler.query(statement,mappedStatement);
        }

        if(results!=null){
            System.out.println("执行成功");
        }else{
            System.out.println("查询失败");
        }
    }catch (Exception e){
        System.out.println("出现异常了");
        e.printStackTrace();
    } finally {
        if(rs !=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement !=null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    return  results; //返回结果集   就是可以直接使用的结果
}

    @Override
    protected int queryCountFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param)throws Exception {
        Connection connection;
        Statement statement = null; //为什么不用preparedStatement？  自己想想
        ResultSet rs = null;
        //  MappedStatement mappedStatement;
        try {
            // 连接的获取
            connection = getConnection(configuration);
            //todo sql的获取 (sqlSource 和SqlNode的处理流程) 这一块是比较困难的  得要两节课才能搞明白
            //String sql=getSql(mappedStatement); //根据mapper的信息来获取到
            SqlSource sqlSource = mappedStatement.getSqlSource();

            String sql = boundSql.getSql();
            StatementHandler statementHandler = configuration.newStatementHandler(mappedStatement.getStatementType());
            statement = statementHandler.prepare(sql, connection);
            //todo 设置参数
            statementHandler.parameterize(param, statement, boundSql);
            PreparedStatement preparedStatement=(PreparedStatement)statement;
            rs=preparedStatement.executeQuery();
            int count=0;
            if(rs.next()){
                count=rs.getInt(1);
                return  count;
            }
            return 0;
        } finally {
            if(rs !=null){
                try {
                    rs.close();
                    System.out.println("关闭了rs");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(statement !=null){
                try {
                    statement.close();
                    System.out.println("关闭了句柄");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }


    //获取连接
    private Connection getConnection(Configuration configuration) {
        DataSource dataSource=configuration.getDataSource();
        Connection connection= null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(connection!=null){
            System.out.println("连接成功");
        }
        return connection;
    }

    @Override
    protected int updateDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param)throws Exception {
        System.out.println("调用updateDataBase");
        Connection connection;
        Statement statement=null; //为什么不用preparedStatement？  自己想想
        int result=0;
        try {
            connection=getConnection(configuration);
            //todo 重点是实现数据源的修改  把select修改成 insert
            SqlSource sqlSource =mappedStatement.getSqlSource();
            String sql=boundSql.getSql();
            System.out.println("从updateDataBase类中拿到最后处理好的sql语句"+sql);
            StatementHandler statementHandler=configuration.newStatementHandler(mappedStatement.getStatementType());
            statement =statementHandler.prepare(sql,connection);  //这里也是不用管的
            //todo 设置参数
            statementHandler. parameterize(param,statement,boundSql);
            //执行statem
            // rs= executeQuery(statement);
            //处理结果  使用到了映射
            //handlerResult(rs,mappedStatement,results);
            //statementHandler直接处理后 就可以处理结果
            PreparedStatement preparedStatement = (PreparedStatement) statement;//为什么要转换呢？
            System.out.println("正在执行");

            //todo 这是执行update lnsert delete的地方
            result =preparedStatement.executeUpdate();
            System.out.println("返回的影响行："+result+" 行");
            //results= statementHandler.query(statement,mappedStatement);
        }catch (Exception e){
            System.out.println("出现异常了");
            e.printStackTrace();
        }finally {
            if(statement !=null){
                try {
                    statement.close();
                    System.out.println("关闭了句柄");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return result;}
    }

}
