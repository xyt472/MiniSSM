package com.lyt;

import cn.hutool.http.HttpRequest;
import com.lyt.connector.httpRequest;
import com.lyt.servlet.DispatcherServlet;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class dispatchServlet extends DispatcherServlet {

    controllerTest controllerTest=new controllerTest();



    @Override
    public void init() {


        //这里的话就可以作为spring的入口
        //mybatis的初始化 和spring容器的初始化

    }

    @Override
    public void doDispath(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 获取controller
         * 使用反射机制获取controller类里面的所有方法
         * 通过 uri映射到相应的 controller里面的方法  （进行 方法的预处理，如设置参数 ） 然后进行调用
         * 将该方法的返回值 使用PrinterWriter 直接返回到前端
         */
        Map <String ,String> parameterMap=new HashMap<>();

        //


    }
}
