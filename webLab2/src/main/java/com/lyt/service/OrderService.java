package com.lyt.service;

import com.lyt.BabyBatisFramework.Annotation.Delete;
import com.lyt.BabyBatisFramework.Annotation.Insert;
import com.lyt.BabyBatisFramework.Annotation.Select;
import com.lyt.BabyBatisFramework.Annotation.Update;
import com.lyt.dao.pojo.AddGoodsList;
import com.lyt.dao.pojo.Order;
import com.lyt.vo.Result;

import java.util.List;

public interface OrderService {



    int insertOrder(Order order);


    int updateOrder(Order order);


    int deleteOrder(int orderId);



    Order queryOrder(int orderId);



    List queryAllOrder();

    Result purchase(AddGoodsList addGoodsList);
}
