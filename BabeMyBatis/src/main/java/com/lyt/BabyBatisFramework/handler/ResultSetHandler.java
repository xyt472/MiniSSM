package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.mapping.ResultMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 专门用来处理结果集
 *
 */
public interface ResultSetHandler {
    List<Object> handleResultSet(PreparedStatement preparedStatement, ResultSet rs, MappedStatement mappedStatement) throws Exception;
    List<Object> handleNestedResultSet(PreparedStatement preparedStatement, ResultSet rs, MappedStatement mappedStatement, ResultMap resultMap)throws Exception;
}
