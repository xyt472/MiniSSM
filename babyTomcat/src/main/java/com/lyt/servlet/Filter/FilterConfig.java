package com.lyt.servlet.Filter;

import com.lyt.servlet.ServletContext;

import java.util.Enumeration;

public interface FilterConfig {
    public String getFilterName();

    public ServletContext getServletContext();
    public String getInitParameter(String name);
    public Enumeration<String> getInitParameterNames();
}
