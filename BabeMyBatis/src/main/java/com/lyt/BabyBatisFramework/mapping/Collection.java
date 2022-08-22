package com.lyt.BabyBatisFramework.mapping;

import lombok.Data;

import java.util.List;

@Data
public class Collection {
    private  String ofType;  //嵌套的对象类型  list或者对象
    private  Class ofTypeClass;
    private  String resultProperty;//  被嵌套的对象属性
    private List<ColumnResult> columnResults;

    public Collection(String ofType, Class ofTypeClass, String resultProperty, List<ColumnResult> columnResults) {
        this.ofType = ofType;
        this.ofTypeClass = ofTypeClass;
        this.resultProperty = resultProperty;
        this.columnResults = columnResults;
    }
}
