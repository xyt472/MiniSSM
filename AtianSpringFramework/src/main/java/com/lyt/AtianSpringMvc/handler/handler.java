package com.lyt.AtianSpringMvc.handler;



import com.lyt.AtianSpringMvc.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handler 是处理器，和 Java Servlet 扮演的角色一致。其作用是执行相关的请求处理逻辑，
 * 并返回相应的数据和视图信息，将其封装至 ModelAndView 对象中。
 * 所有组件中，需要开发人员进行开发的是处理器（Handler，常称Controller）和视图（View）。
 * 通俗的说，要开发处理该请求的具体代码逻辑，以及最终展示给用户的界面。
 */
public interface handler {
    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
