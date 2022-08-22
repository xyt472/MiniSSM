package com.lyt.BabyBatisFramework.config;

import com.lyt.BabyBatisFramework.executor.CachingExecutor;
import com.lyt.BabyBatisFramework.executor.Executor;
import com.lyt.BabyBatisFramework.executor.SimpleExecutor;
import com.lyt.BabyBatisFramework.handler.*;
import com.lyt.BabyBatisFramework.mapping.ResultMap;
import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装了mybatis中xml文件的所有信息
 *
 */
@Data
public class Configuration {
    private DataSource dataSource;
    private boolean useCache=true;  //是否使用缓存 默认开启
    private Map<String,MappedStatement> mappedStatements =new HashMap<String, MappedStatement>();
    private Map<String,ResultMap> resultMaps=new HashMap<>();
    public ResultMap getResultMap(String id){
        return resultMaps.get(id);  //每一个resultMap都有一个id
    }

    public void addResultMap(String id, ResultMap resultMap){
        this.resultMaps.put(id,resultMap);
        System.out.println("已经放入resultMap");
        System.out.println("这里的Id是："+id);
    }

    public MappedStatement getMappedStatementsById(String statementId) {
//        for (MappedStatement value : mappedStatements.values()) {
//            System.out.println("==============遍历 mappedStatements  "+value);
//        }


        //取出对象
        return mappedStatements.get(statementId);  //前提是list不为空才行
    }
  //mappedStatement封装的是映射文件中的select update等标签4个statement标签
    public void addMappedStatement(String statementId,MappedStatement mappedStatement) {

        this.mappedStatements.put(statementId, mappedStatement);
    }
    public Executor newExecutor(String executeType) {
        //先判断 类型  如果没有传参数即空字符串，那就是默认的类型 simple  否者就按照传入的类型来选择执行器执行
        executeType=executeType==null||executeType.equals("")?"simple":executeType;
        Executor executor=null;
        if(executeType.equals("simple")){
            System.out.println("是simple类型");
            executor=new SimpleExecutor();
        }
        //针对真正的执行器进行二级缓存包装  目的是为了提高性能 可以用也可不用 但是一般都会走二级缓存
        //一级缓存一定会走，因为一级缓存是父类
        if(useCache){

            executor=new CachingExecutor(executor); //委托给真正的处理器去处理
        }
        return executor;
    }

    public StatementHandler newStatementHandler(String statementType) {
        statementType=statementType==null||statementType.equals("")?"prepared":statementType;
        //还可以写一个 RoutingStatementHnadler 路由控制器来进行进一步封装   源码就是这么实现的
        //这里使用到了路由跳转的方式
        StatementHandler statementHandler=new RoutingStatementHandler(statementType,this);
//        if(statementType.equals("prepared")){
//            return new PreparedStatementHandler(this); //把当前操作的configuration对象传进去
//        }
        return statementHandler;
    }

    public ParameterHandler newParameterHandler() {

    return new DefaultParameterHandler();
    }

    public ResultSetHandler newResultSetHandler() {

    return new DeafaultResultSetHandler();
    }
}
