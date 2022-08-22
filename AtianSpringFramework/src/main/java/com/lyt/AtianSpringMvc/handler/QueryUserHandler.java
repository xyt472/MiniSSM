package com.lyt.AtianSpringMvc.handler;



import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public class QueryUserHandler implements HttpRequestHandler{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write("湖人总冠军-QueryUserHandler");
    }
}
