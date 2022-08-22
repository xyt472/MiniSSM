package com.lyt.BabyBatisFramework.executor;

import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;

import java.util.List;

public class BatchExecutor extends BaseExecutor{
    @Override
    protected List<Object> queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
        return null;
    }

    @Override
    protected int queryCountFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) throws Exception {
        return 0;
    }

    @Override
    protected int updateDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
        return 0;
    }


}
