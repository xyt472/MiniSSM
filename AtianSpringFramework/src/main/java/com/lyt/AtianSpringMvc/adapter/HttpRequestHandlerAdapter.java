package com.lyt.AtianSpringMvc.adapter;

import com.lyt.AtianSpring.Annotation.Component;
import com.lyt.AtianSpringMvc.handler.HttpRequestHandler;
import com.lyt.AtianSpringMvc.model.ModelAndView;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;


@Component
public class HttpRequestHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HttpRequestHandler);
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ((HttpRequestHandler)handler).handleRequest(request,response);
        return null;
    }
}
