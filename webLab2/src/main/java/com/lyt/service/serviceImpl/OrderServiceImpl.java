package com.lyt.service.serviceImpl;

import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Service;
import com.lyt.dao.mapper.OrderMapper;
import com.lyt.dao.mapper.ProductMapper;
import com.lyt.dao.pojo.AddGoodsList;
import com.lyt.dao.pojo.Order;
import com.lyt.service.OrderService;
import com.lyt.vo.Result;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public int updateOrder(Order order) {
        return 0;
    }

    @Override
    public int deleteOrder(int orderId) {
        return 0;
    }

    @Override
    public Order queryOrder(int orderId) {
        return null;
    }

    @Override
    public List queryAllOrder() {
        return null;
    }

    @Override
    public Result purchase(AddGoodsList addGoodsList) {
        //先查库存
        Order order=new Order();
        int left= productMapper.getProductLeft(addGoodsList.getGoodsId());
        if(addGoodsList.getNum()>left){
            return  Result.fail(500,"库存不够，购买失败");
        }else {
            //成功购买   库存减少  更新库存（根据商品id）
            Date now = new Date();
            SimpleDateFormat f1 = new SimpleDateFormat("yyyy-mm-dd");
            //设置时间戳 格式为 时间（毫秒）+ 用户id
            SimpleDateFormat f2 = new SimpleDateFormat("yyyymmddHHmmss");
            order.setCreateDate(f1.format(now));
            order.setOrderId(f2.format(now)+addGoodsList.getUid());
            order.setGoodsId(addGoodsList.getGoodsId());
            order.setOrderAmount(addGoodsList.getOrderAmount());

            System.out.println("+++++++++++oorder 是"+order);
            //插入订单表
            orderMapper.insertOrder(order);
            //更新库存表
            productMapper.updateProductLeft(String.valueOf(left-addGoodsList.getNum()),addGoodsList.getGoodsId());
            return Result.success("购买成功");

        }

    }

    @Test
    public void testDate(){
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyymmddHHmmss");
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-mm-dd");

        System.out.println(f.format(now));
        System.out.println(f1.format(now));

    }

}
