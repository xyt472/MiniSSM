package com.lyt.controller;

import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Controller;
import com.lyt.AtianSpringMvc.annotation.RequestBody;
import com.lyt.AtianSpringMvc.annotation.RequestMapping;
import com.lyt.AtianSpringMvc.annotation.ResponseBody;
import com.lyt.dao.pojo.AddGoodsList;
import com.lyt.service.OrderService;
import com.lyt.vo.Result;

@Controller
@RequestMapping("order")
public class OderController {

    @Autowired
    OrderService orderService;

    @ResponseBody
    @RequestMapping("purchase")
    public Result purchase(@RequestBody AddGoodsList addGoodsList ){
        System.out.println("+++++++++++OderController_+_+_+_+_+_+_");
        return  orderService.purchase(addGoodsList);
    }

}
