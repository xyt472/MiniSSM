package com.lyt.servlet;

import java.util.Map;

public interface ServletRequest {

    public String getParameter(String name);
    String getMethod();
}
