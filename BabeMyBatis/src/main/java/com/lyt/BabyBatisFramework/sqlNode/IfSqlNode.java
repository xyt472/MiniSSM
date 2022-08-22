package com.lyt.BabyBatisFramework.sqlNode;


import com.lyt.BabyBatisFramework.config.DynamicContext;

/**
 * 封装了带有if标签的动态标签
 */
public class IfSqlNode implements SqlNode {
    private String test;
    //获得sql语句
    private SqlNode mixedSqlNode;
    public IfSqlNode(String test,SqlNode sqlNode){
        this.test=test;
        this.mixedSqlNode=sqlNode;
    }
    //有待实现
    @Override
    public void apply(DynamicContext context) {
        //使用OGNL对test中的内容进行判断 （test属性值写的就是ongl表达式的语法）
        Object parameter =context.getBinding().get("_parameter");
//        if(parameter==null){
//            System.out.println("Object parameter =context.getBinding().get(\"_parameter\")结果是空");
//        }
        //这个方法还不能用
        //得使用ognl来实现  parameter是入参对象
        boolean  b=true;
        if(b){
            try {
                mixedSqlNode.apply(context);   //递归调用
            } catch (Exception e) {
                System.out.println("mixedSqlNode出现问题");
                e.printStackTrace();
            }
        }

    }
}
