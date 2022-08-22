package com.lyt.AtianSpringMvc.servlet;

import com.lyt.servlet.HttpServlet;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;

import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Map;

/**
 * springmvc提供的唯一的一个Servlet类   通过调用get 或者post 来调用 doDispath
 */
public abstract class AbstractServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行了 doget");
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
       //getRequestAndResponse(req, resp);
        System.out.println("调用 doDispath");
        doDispath(req, resp);
    }

    public abstract void doDispath(HttpServletRequest request,HttpServletResponse response) ;
    public abstract Map<String ,Object> getRequestAndResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}

