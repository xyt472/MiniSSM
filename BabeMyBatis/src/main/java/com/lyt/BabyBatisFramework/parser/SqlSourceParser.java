package com.lyt.BabyBatisFramework.parser;

import com.lyt.BabyBatisFramework.SqlSource.SqlSource;
import com.lyt.BabyBatisFramework.SqlSource.StaticSqlSource;
import com.lyt.BabyBatisFramework.Utils.GenericTokenParser;
import com.lyt.BabyBatisFramework.Utils.PareameterMappingTokenHandler;

/**
 * 用来处理#{}之后，获取 staticSqLSource
 */
public class SqlSourceParser {
    public SqlSource parse(String sqlText){
        //2调用sqlsource的处理逻辑 。对于#{}进行处理
        //可能还会有#{}   原理和TextSqlNode差不多 吧$换成#即可
        PareameterMappingTokenHandler tokenHandler=new PareameterMappingTokenHandler();
        GenericTokenParser tokenParser=new GenericTokenParser("#{","}",tokenHandler);
        String sql= null;
        try {
            sql = tokenParser.parse(sqlText);
            //System.out.println("这是处理过#{}的sql ："+sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StaticSqlSource(sql,tokenHandler.getParameterMappings());
    }
}
