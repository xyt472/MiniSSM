package com.lyt.BabyBatisFramework.SqlSource;


import com.lyt.BabyBatisFramework.config.BoundSql;

public interface SqlSource {

    /**
     * 针对封装的sql信息 ，去进行解析 ，获取可以直接执行的sql语句
     * @param param
     * @return
     */
    BoundSql getBoundSql(Object param) throws Exception;

}
