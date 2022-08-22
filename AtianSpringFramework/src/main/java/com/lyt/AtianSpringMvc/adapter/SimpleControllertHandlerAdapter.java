package com.lyt.AtianSpringMvc.adapter;


import com.lyt.AtianSpringMvc.handler.SimpleControllerHandler;
import com.lyt.AtianSpringMvc.model.ModelAndView;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;


public class SimpleControllertHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof SimpleControllerHandler);
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ((SimpleControllerHandler)handler).handleRequest(request,response);
    }
}
