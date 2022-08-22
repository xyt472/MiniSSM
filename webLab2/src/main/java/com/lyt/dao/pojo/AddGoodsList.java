package com.lyt.dao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class AddGoodsList {
    private  String goodsId;
    private String uid;
    private Integer  num;
    private Integer orderAmount;
    private List<Product> products;
}
