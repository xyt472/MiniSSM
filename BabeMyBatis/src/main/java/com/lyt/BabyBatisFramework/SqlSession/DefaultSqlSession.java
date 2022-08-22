package com.lyt.BabyBatisFramework.SqlSession;

import com.lyt.BabyBatisFramework.Annotation.Proxy.MyInvocationHandlerMybatis;
import com.lyt.BabyBatisFramework.Utils.objectToMap;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.executor.Executor;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession{
    //依赖注入


    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {

        this.configuration=configuration;

    }

    //获取代理类
    public static  <T> T getMapper(Class classz) {
        return (T) Proxy.newProxyInstance(classz.getClassLoader(), new Class[]{classz},
                new MyInvocationHandlerMybatis(classz));
    }

    @Override
    public <T> List<T> selectList(String statementId,Object param)throws  Exception {
        //todo 重点在这里 实现查询
        if( !(param instanceof Map))
        {
            param = objectToMap.toMap(param);
        }
        MappedStatement mappedStatement=configuration.getMappedStatementsById(statementId);
        if(mappedStatement==null){
            System.out.println("在selectList中mappedStatement是空的");
        }
        //Executor可以有多种类型 比如SimpleExecutor 、ReuseExecutoy、BatcExecutor
        //可以通过全局配置文件中的setting标签去指定 （这个信息被存储在configuration对象当中）
        //也可以通过创建Sqlsession时通过指定executor为以上三种类型
        Executor executor=configuration.newExecutor(null);//参数可以是你想指定的类型  这里会走二级缓存
        if(executor==null){
            System.out.println("executor是空的");
        }else {
            System.out.println("executor非空");
        }
        //通过Executor去处理jdbc的操作
        return executor.query(configuration,mappedStatement,param);

    }

    //查询单个
    @Override
    public <T> T selectOne(String statementId, Object param) throws Exception{
        if( !(param instanceof Map))
        {
            param = objectToMap.toMap(param);
        }
        List <Object> list =this.selectList(statementId,param);
        if(list!=null&&list.size()==1){
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public int selectCount(String statementId,Object param)throws Exception{
        System.out.println("_+_+_+_++传进来的 statementId "+statementId);
        MappedStatement mappedStatement=configuration.getMappedStatementsById(statementId);
        Executor executor=configuration.newExecutor(null);//参数可以是你想指定的类型  这里会走二级缓存
        if(executor!=null){
          return executor.queryCount(configuration,mappedStatement,param);
        }
        return 0;
    }


    //只能以map的方式传参数
    @Override
    public int update(String statementId, Object param) {
        //有风险
        if( !(param instanceof Map))
        {
            System.out.println("将javabena转为map");
            param = objectToMap.toMap(param);
        }

        //todo 重点在这里 实现查询    为什么这样才可以查到？
        MappedStatement mappedStatement=configuration.getMappedStatementsById(statementId);
      //  MappedStatement mappedStatement=configuration.getMappedStatementsById(statementId);
        System.out.println("update传入的statementId是："+statementId);
        if(mappedStatement==null){

            System.out.println("在defaultSqlSession中mappedStatement是空的");
        }
        //Executor可以有多种类型 比如SimpleExecutor 、ReuseExecutoy、BatcExecutor
        //可以通过全局配置文件中的setting标签去指定 （这个信息被存储在configuration对象当中）
        //也可以通过创建Sqlsession时通过指定executor为以上三种类型
       Executor executor=configuration.newExecutor(null);//参数可以是你想指定的类型
//        Executor executor=new SimpleExecutor();
//        if(executor==null){
//            System.out.println("executor是空的");
//        }else {
//            System.out.println("executor非空");
//        }
        //通过Executor去处理jdbc的操作
       //
        int n=0;
        try {
          n= executor.update(configuration,mappedStatement,param);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  n;
    }
}
