package com.lyt.BabyBatisFramework.Factory;

import com.lyt.BabyBatisFramework.SqlSession.SqlSession;

public interface SqlSessionFactory {

    SqlSession openSession();

}
