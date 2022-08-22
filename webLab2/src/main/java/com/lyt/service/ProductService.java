package com.lyt.service;


import com.lyt.dao.pojo.AddGoodsList;
import com.lyt.dao.pojo.Product;
import com.lyt.vo.QueryInfo;
import com.lyt.vo.Result;

import java.util.Map;


public interface ProductService {
    public Product getProductById(String id) throws Exception;
    public int getProductCounts() throws Exception;
    public Map getAllProducts(QueryInfo queryInfo) throws Exception;
    public Map getAllProducts2() throws Exception;
    public String updateState(String id, String state);
    public String addProduct(Product product);
    public String getUpdateProduct(String id) throws Exception;
    public String editProduct(Product product);
    public String deleteProduct(String id);
    public int addGoodsList(AddGoodsList addGoodsList);


    int IsExistence(AddGoodsList addGoodsList);

    int updateGoodsNums(AddGoodsList addGoodsList);
    Object getGoodsList(QueryInfo queryInfo);

    Result productService(AddGoodsList addGoodsList);
}
