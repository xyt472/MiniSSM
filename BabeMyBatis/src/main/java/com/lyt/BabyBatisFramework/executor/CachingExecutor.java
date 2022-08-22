package com.lyt.BabyBatisFramework.executor;

import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;

import java.util.List;

public class CachingExecutor implements  Executor{
    private Executor executor; //委托模式 委托给真正的执行器去处理

    public CachingExecutor(Executor executor) {
        this.executor = executor;
    }

    /**
     * 处理二级缓存
     * @param configuration
     * @param mappedStatement
     * @param param
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param)throws Exception {
       //先获取二级缓存对象
        //二级缓存中没有，则继续调用执行器的逻辑
        return executor.query(configuration,mappedStatement,param);
    }

    @Override
    public int queryCount(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {
        return executor.queryCount(configuration,mappedStatement,param);
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {
        return executor.update(configuration,mappedStatement,param);
    }

}
