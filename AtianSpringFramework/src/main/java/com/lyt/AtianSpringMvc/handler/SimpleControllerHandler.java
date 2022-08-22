package com.lyt.AtianSpringMvc.handler;


import com.lyt.AtianSpringMvc.model.ModelAndView;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;




import java.io.IOException;

/**
 * 指定处理类编写规范(可以针对返回值进行二次处理)
 */
public interface SimpleControllerHandler {
    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws  IOException;
}
