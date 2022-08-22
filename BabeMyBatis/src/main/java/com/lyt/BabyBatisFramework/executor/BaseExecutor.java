package com.lyt.BabyBatisFramework.executor;

import com.lyt.BabyBatisFramework.SqlSource.SqlSource;
import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Executor可以有多种类型 比如SimpleExecutor 、ReuseExecutor、BatcExecutor 这三个类都会去走该类的一级缓存处理
 * 现在要去实现他
 */
//为什么要使用抽象呢？？
public abstract class BaseExecutor implements Executor{
    //一级缓存  key 是sql语句  ，value 是查到的结果
    private Map<String,List<Object>> oneLevelCache=new HashMap<>();

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
        //获取缓存key
        SqlSource sqlSource=mappedStatement.getSqlSource();
        BoundSql boundSql= null;
        try {
            boundSql = sqlSource.getBoundSql(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(boundSql==null){
            System.out.println("boundSql是空的");
        }
        String cacheKey =getCachekey(boundSql);
         List<Object> list=oneLevelCache.get(cacheKey);
         if(list!=null){
             System.out.println("从缓存中取数据");
             return (List<T>)list;  //强制类型转换
         }
         //查询数据库
         list= queryFromDataBase(configuration,mappedStatement,boundSql,param);
         oneLevelCache.put(cacheKey,list);
        return (List<T>)list;
    }
    protected abstract List<Object> queryFromDataBase(Configuration configuration, MappedStatement mappedStatement ,BoundSql boundSql,Object param);

    protected String getCachekey(BoundSql boundSql){
        // todo catch 需要做特殊处理
        //输出的sql语句： boundSql.getValue();  -->select *from user where username=?
        return boundSql.getSql() ;
    }

    @Override
    public int queryCount(Configuration configuration, MappedStatement mappedStatement, Object param)throws Exception{
        SqlSource sqlSource=mappedStatement.getSqlSource();
        BoundSql boundSql= sqlSource.getBoundSql(param);


        int n=queryCountFromDataBase(configuration,mappedStatement,boundSql,param);
        return n;
    }
    protected abstract int queryCountFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) throws Exception;

    ;


    //todo  根据配置信息插入数据
    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {


        SqlSource sqlSource=mappedStatement.getSqlSource();

        BoundSql boundSql= null;
        int result=0;
        System.out.println("调用update");
        try {
            boundSql = sqlSource.getBoundSql(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(boundSql==null){
            System.out.println("boundSql是空的");
        }
        String cacheKey =getCachekey(boundSql);
        List<Object> list=oneLevelCache.get(cacheKey);
        //插入数据
        result= updateDataBase(configuration,mappedStatement,boundSql,param);
        oneLevelCache.put(cacheKey,list);
        return result;


    }

    protected abstract int updateDataBase(Configuration configuration, MappedStatement mappedStatement ,BoundSql boundSql,Object param) throws Exception;

}
