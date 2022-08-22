package com.lyt.BabyBatisFramework.config;

import com.lyt.BabyBatisFramework.SqlSource.SqlSource;
import lombok.Data;

/**
 * 封装了statement标签中的信息  有多少种就写多少个 这两个配置类非常的重要
 */
@Data
public class MappedStatement {
    private String statementId;
    private  String statementType;
    private  String resultType;
    private Class  resultTypeClass;
    private SqlSource sqlSource;
    private  String resultMap;


    public MappedStatement(String statementId, Class resultTypeClass, String statementType,SqlSource sqlSource) {
        this.statementId = statementId;
        this.statementType = statementType;
        this.resultTypeClass = resultTypeClass;
        this.sqlSource = sqlSource;
    }

}
