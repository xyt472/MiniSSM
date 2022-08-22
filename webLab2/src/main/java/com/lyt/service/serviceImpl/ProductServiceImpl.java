package com.lyt.service.serviceImpl;


import com.alibaba.fastjson.JSON;
import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Service;
import com.lyt.dao.mapper.ProductMapper;
import com.lyt.dao.pojo.AddGoodsList;
import com.lyt.dao.pojo.Product;
import com.lyt.service.ProductService;
import com.lyt.vo.QueryInfo;
import com.lyt.vo.Result;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;



    //注入mapper  //如何实现自动注入呢？
   // private ProductMapper productMapper2= DefaultSqlSession.getMapper(ProductMapper.class);

//    private ProductMapper productMapper2= (ProductMapper)(Proxy.newProxyInstance(ProductMapper.class.getClassLoader(),
//            new Class[]{ProductMapper.class}, new MyInvocationHandlerMybatis(ProductMapper.class)));
    //这行可以扔了
   // private static ProductMapper productDao=new ProductMapperImpl();
    @Override
    public Product getProductById(String id) throws Exception {
        Product product  =productMapper.getProductById(id);
     //   String productJsonString= JSON.toJSONString(product);
        return product;
    }

    @Test
    public void TestProxy(){
        try {
            Product product  =productMapper.getProductById("2021");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public int getProductCounts() throws Exception {
        return productMapper.getProductCounts();
    }

    @Override
    public Map getAllProducts(QueryInfo queryInfo) throws Exception {
        HashMap<String,Object> res=new HashMap<>();
        int numbers=productMapper.getProductCounts();
        List<Product> product2List=productMapper.getAllProducts(queryInfo);
        res.put("numbers",numbers);
        res.put("data",product2List);
//        String productsJson = JSON.toJSONString(res);
        return res;
    }

    @Override
    public Map<String, Object> getAllProducts2() throws Exception {
        HashMap<String,Object> res=new HashMap<>();
         int numbers=productMapper.getProductCounts();
       // int numbers=7;  //这个因为没有走缓存导致了卡顿？？
        List list=productMapper.getAllProducts2();
        res.put("numbers",numbers);
        res.put("data",list);
        return res;
    }

    @Override
    public String updateState(String id, String state) {
        return null;
    }


    @Override
    public String addProduct(Product product) {

        int i = productMapper.addProduct(product);
        if(i>0){
            System.out.println("插入product成功");
        }
        String str = i >0?"success":"error";
        return str;
    }

    @Override
    public String getUpdateProduct(String id) throws Exception {

      Product product = productMapper.getUpdateProduct(id);
        String users_json = JSON.toJSONString(product);
        return users_json;
    }



    @Override
    public String editProduct(Product product) {
        int i = productMapper.editProduct(product);
        String str = i >0?"success":"error";
        return str;
    }

    @Override
    public String deleteProduct(String id) {

        System.out.println(id);
        int i = productMapper.deleteProduct(id);
        String str = i >0?"success":"error";
        return str;
    }

    @Override
    public int addGoodsList(AddGoodsList addGoodsList) {
        System.out.println("调用 serivice ");
        //先查一下数据库有没有这个商品的数据
        if( productMapper.IsExistence(addGoodsList) >0){
            System.out.println("更新购物车");
            //存在这个商品的话就 直接更新 购物车中的数据
         int i=   productMapper.updateGoodsNums(addGoodsList);
         if(i>0){
             System.out.println("———————++++++++++++++———————————————————————————————————————更新成功");
         };
        }else {
            System.out.println("插入购物车"  );
            productMapper.addGoodsList(addGoodsList);
        }
        return 1;
    }

    @Override
    public int IsExistence(AddGoodsList addGoodsList) {
        return productMapper.IsExistence(addGoodsList);
    }

    @Override
    public int updateGoodsNums(AddGoodsList addGoodsList) {
        return productMapper.updateGoodsNums(addGoodsList);
    }

    @Override
    public Object getGoodsList(QueryInfo queryInfo) {
        return productMapper.getGoodsList(queryInfo);
    }

    @Override
    public Result productService(AddGoodsList addGoodsList) {
        //先查库存
       int left= productMapper.getProductCounts();
       if(addGoodsList.getNum()>left){
           return  Result.fail(500,"库存不够");
       }else {
           //成功购买   库存减少  更新库存（根据商品id）

           //插入订单表

       }
        return null;
    }
}
