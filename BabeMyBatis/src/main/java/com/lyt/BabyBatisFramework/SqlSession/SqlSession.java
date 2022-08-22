package com.lyt.BabyBatisFramework.SqlSession;

import java.util.List;


public interface SqlSession {
    /**
     * 根据参数查询信息列表
     * @param s
     * @param param
     * @param <T>
     * @return
     */
    <T>List<T> selectList(String s, Object param) throws Exception;

    /**
     * 根据参数查询单个信息
     *
     * @param s
     * @param param
     * @param <T>
     * @return
     */
    <T> T selectOne(String s, Object param) throws Exception;

    int update(String s, Object parem);
    public int selectCount(String statementId,Object param)throws Exception;

}
