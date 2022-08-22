package com.lyt.BabyBatisFramework.Factory;

import com.lyt.BabyBatisFramework.SqlSession.DefaultSqlSession;
import com.lyt.BabyBatisFramework.SqlSession.SqlSession;
import com.lyt.BabyBatisFramework.config.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
//这叫依赖注入
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration=configuration;

    }

    @Override
    public SqlSession openSession() {
        SqlSession sqlSession=new DefaultSqlSession(configuration);
        return sqlSession;
    }
}
