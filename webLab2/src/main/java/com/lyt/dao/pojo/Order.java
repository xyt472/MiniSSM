package com.lyt.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Order {

  private String orderId;
  private String goodsId;
  private String createDate;
  private Integer orderAmount;



}
