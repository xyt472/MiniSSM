package com.lyt.BabyBatisFramework.sqlNode;


import com.lyt.BabyBatisFramework.config.DynamicContext;

public interface SqlNode {
    /**
     * 处理sql节点  被TextSqlNode类重写了
     * @param context
     */
    public  void apply(DynamicContext context) throws Exception;

}
