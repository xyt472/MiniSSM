package com.lyt.servlet;

import java.io.Serializable;
import java.util.Enumeration;

public abstract class GenericServlet implements Servlet, ServletConfig, Serializable {
    @Override
    public void init(ServletConfig config) throws Exception {

    }

    @Override
    public abstract void service(ServletRequest req, ServletResponse res) ;

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public String getServletName() {
        return null;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public String getInitParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return null;
    }
}
