package com.lyt.Processor;

import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;

public class StaticProcessor implements Processor {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        response.sendRedirect();
    }

}
