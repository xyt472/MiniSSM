package com.lyt.BabyBatisFramework.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态上下文
 *
 */
public class DynamicContext {
    private StringBuffer sb=new StringBuffer();
    private Map<String,Object> binding =new HashMap<String, Object>();

    public DynamicContext(Object param){
        //为了处理$#时，需要用到入参
//        if(param==null){
//            System.out.println("传入的param是空的 作为一个构造参数使用");
//        }
        this.binding.put("_parameter",param);
    }

    public String getSql() {
        return sb.toString();
    }

    public void appendSql(String sqlText) {
        this.sb.append(sqlText); //追加sql文本
        this.sb.append(" ");  //分隔开文本  防止sql 语句挨在一起
      //  System.out.println("(StringBuffer字符流？)DynamicContext追加文本后的sql "+sb.toString());
    }

    public Map<String, Object> getBinding() {
        return binding;
    }

}
