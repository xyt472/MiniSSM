package com.lyt.BabyBatisFramework.mapping;

import lombok.Data;

import java.util.List;

@Data
public class ResultMap {
    //这些参数是针对  嵌套映射准备的
   // private  String resultMap;   //与select标签中对应的属性
    private  String id;
    private  Class type;  //映射的主类型
    private  String idColumn;    //查询出来的 主对象的唯一id列名
    private  String idProperty;    //查询出来的 主对象的唯一id列名
    private  List<ColumnResult> columnResults;
    private boolean hasNestedResultMaps;   //标记是否为嵌套查询  如果是的话就为真
    private Collection collection;
    private int idSize;
    private int columnResultSize;
    private String collectionName;

}
