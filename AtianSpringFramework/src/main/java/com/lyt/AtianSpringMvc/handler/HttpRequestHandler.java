package com.lyt.AtianSpringMvc.handler;


import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;


/**
 * 指定处理类编写规范
 */
public interface HttpRequestHandler {
    void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
