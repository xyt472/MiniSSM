package com.lyt.BabyBatisFramework.sqlNode;



import com.lyt.BabyBatisFramework.config.DynamicContext;

import java.util.List;

/**
 * 封装了一组sqlNode  为什么是命名为mixed？
 * 因为里面有文本节点 也有if节点 他们可能是无规律的混合在一起的
 */
public class MixedtSqlNode implements SqlNode {
    private String sqlText;
   private List<SqlNode> sqlNodeList;

    public MixedtSqlNode( List <SqlNode>sqlNodeList){
        this.sqlNodeList=sqlNodeList;
    }
    //有待实现
    @Override
    public void apply(DynamicContext context) {
        //很简单，就是执行apply就行
        for (SqlNode sqlNode :sqlNodeList ){
            try {
                sqlNode.apply(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
