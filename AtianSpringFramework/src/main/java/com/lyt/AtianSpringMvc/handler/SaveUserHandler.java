package com.lyt.AtianSpringMvc.handler;


import com.lyt.AtianSpringMvc.model.ModelAndView;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;


import javax.servlet.ServletException;
import java.io.IOException;

public class SaveUserHandler implements SimpleControllerHandler {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write("湖人总冠军-SaveUserHandler");
        return null;
    }
}
