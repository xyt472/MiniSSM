package com.lyt.dao.mapper;

import com.lyt.AtianSpring.Annotation.DAO;
import com.lyt.BabyBatisFramework.Annotation.Delete;
import com.lyt.BabyBatisFramework.Annotation.Insert;
import com.lyt.BabyBatisFramework.Annotation.Select;
import com.lyt.BabyBatisFramework.Annotation.Update;
import com.lyt.dao.pojo.Order;

import java.util.List;
@DAO
public interface OrderMapper {



    @Insert("order.insertOrder")
    int insertOrder(Order order);

    @Update("order.updateOrder")
    int updateOrder(Order order);

    @Delete("order.deleteOrder")
    int deleteOrder(int orderId);


    @Select(statementId = "order.queryOrder",type = "selectOne")
    Order queryOrder(int orderId);


    @Select(statementId="order.queryAllOrder" ,type="selectList")
    List queryAllOrder();




}
