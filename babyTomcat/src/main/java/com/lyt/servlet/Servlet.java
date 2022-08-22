package com.lyt.servlet;

import java.io.IOException;

public interface Servlet {
    /**
     * servlet初始化
     */
    public void init(ServletConfig config) throws Exception;
    /**
     * servlet业务处理
     */
   // void service(HttpServletRequest request, HttpServletResponse response);

    public void service(ServletRequest req, ServletResponse res) throws Exception;
    public ServletConfig getServletConfig();
    public String getServletInfo();
    public void destroy();
}
