package com.lyt.dao.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Product {
    private String id;
    private double price;
    private String productName;
    private String info;

    //日期必须加上这个注释 否则在 转换为json的时候 会被自动转换成时间戳的形式
    @JSONField(format = "yyyy-MM-dd ")
    private java.sql.Date productDate;
    private String state;
    private Integer  num;   //要用integer不然会报错
    private int  left;


}

