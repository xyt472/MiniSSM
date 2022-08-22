package com.lyt.BabyBatisFramework.mapping;

import lombok.Data;

@Data
public class ColumnResult {
    private  String column; //查询出来的列的名称
    private String property;//对应的对象属性名称
}
