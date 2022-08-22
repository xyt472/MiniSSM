package com.lyt.BabyBatisFramework.sqlNode;


import com.lyt.BabyBatisFramework.config.DynamicContext;

/**
 * 封装了不带有#$的sql 文本
 */
public class StaticTextSqlNode implements SqlNode{
    private String sqlText;
    public  StaticTextSqlNode(String sqlText){
        this.sqlText=sqlText;
    }
    @Override
    public void apply(DynamicContext context) {
      //  System.out.println("StaticTextSqlNode  追加之前的sql语句："+sqlText);
        context.appendSql(sqlText);  //追加即可
        //System.out.println("StaticTextSqlNode  追加之后的sql语句："+sqlText);
    }
}
