package com.lyt.servlet.Filter;

import com.lyt.servlet.ServletRequest;
import com.lyt.servlet.ServletResponse;


public interface Filter {
    public default void init(FilterConfig filterConfig) throws Exception {}
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) ;

    public  void destroy() ;


}
