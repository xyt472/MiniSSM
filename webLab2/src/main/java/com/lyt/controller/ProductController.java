package com.lyt.controller;

import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Controller;
import com.lyt.AtianSpringMvc.annotation.RequestBody;
import com.lyt.AtianSpringMvc.annotation.RequestMapping;
import com.lyt.AtianSpringMvc.annotation.ResponseBody;
import com.lyt.dao.pojo.AddGoodsList;
import com.lyt.dao.pojo.Product;
import com.lyt.service.ProductService;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.vo.QueryInfo;
import com.lyt.vo.Result;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService ;

    @RequestMapping("updateGoodsNums")
    public String updateGoodsNums( @RequestBody AddGoodsList addGoodsList){
        return null;
    }

    @RequestMapping("addGoodsList")
    public String addGoodsList(@RequestBody AddGoodsList addGoodsList){
        int row= productService.addGoodsList(addGoodsList);
        if(row>0){
            return "ok";
        }
        return "err";
    }

    @RequestMapping("getGoodsList")
    @ResponseBody
    public Object getGoodsList(@RequestBody QueryInfo queryInfo){
        return  productService.getGoodsList(queryInfo);
    }

    @RequestMapping("getAllProducts")
    @ResponseBody
    public Map getAllProducts(@RequestBody QueryInfo queryInfo) throws Exception{
        return productService.getAllProducts(queryInfo);
    }



    @RequestMapping("getAllProducts2")
    @ResponseBody
    public Map getAllProducts2() throws Exception{
      return productService.getAllProducts2();
    }

    @RequestMapping("updateState")
    public void updateState(String id, String state) {
        String str=  productService.updateState(id,state);
    }


    @RequestMapping("getProductCounts")
    public void getProductCounts( String username){
    };


    @RequestMapping("addProduct")
    public void addProduct(@RequestBody Product product)throws Exception{
        String str =productService.addProduct(product);
        //将数据发给前端
    };

    @RequestMapping("getUpdateProduct")
    public void getUpdateProduct(String id)throws Exception{
        String str= productService.getUpdateProduct(id);

    };

    @RequestMapping("editProduct")
    public void editProduct(@RequestBody Product product){
        String str= productService.editProduct(product);
    };


    @RequestMapping("deleteProduct")
    public void deleteProduct(String id){
        String str= productService.deleteProduct(id);
    };
}
