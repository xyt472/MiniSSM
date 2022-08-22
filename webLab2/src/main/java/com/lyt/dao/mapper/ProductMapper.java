package com.lyt.dao.mapper;

import com.lyt.AtianSpring.Annotation.DAO;
import com.lyt.BabyBatisFramework.Annotation.*;

import com.lyt.dao.pojo.AddGoodsList;
import com.lyt.dao.pojo.Product;
import com.lyt.vo.QueryInfo;


import java.util.List;

@DAO
public interface ProductMapper {

    @Update("test.updateGoodsNums")
    int updateGoodsNums(AddGoodsList  addGoodsList);

    @Insert("test.addGoodsList")
    int addGoodsList(AddGoodsList addGoodsList);



    @Select(statementId = "test.IsExistence",type = "selectCount")
    Integer IsExistence(AddGoodsList addGoodsList);


    @Delete("test.deleteProduct")
    int deleteProduct(@Param("id") String id);

    @Select(statementId = "test.getProductCounts",type = "selectCount")
     Integer getProductCounts() ;

    @Select(statementId = "test.getProductById",type = "selectOne")
    Product getProductById(@Param("id") String id) throws Exception;

    @Select(statementId = "test.getAllProducts",type = "selectList")
     List<Product> getAllProducts(QueryInfo queryInfo) throws Exception;

    @Select(statementId = "test.getAllProduct",type = "selectList")
    List<Product> getAllProducts2() throws Exception;

    @Update("test.updateState")
     int updateState(@Param("id") String id, @Param("state") int state);

    @Update("test.addProduct")
     int addProduct(Product product);

    @Select(statementId = "test.getProductById",type = "selectOne")
    Product getUpdateProduct(@Param("id") String id) throws Exception;

    @Update("test.editProduct")
    int editProduct(Product product);

    @Delete("test.deleteProduct")
     int deleteProduct(int id);


    @Select(statementId = "test.getGoodsList",type = "selectList")
    Object getGoodsList(QueryInfo queryInfo);

    @Select(statementId = "test.getProductLeft",type = "selectOne")
    int getProductLeft( @Param("goodsId") String  goodsId);

    @Update("test.updateProductLeft")
    int updateProductLeft(@Param("left") String left,@Param("goodsId")String goodsId);
}
