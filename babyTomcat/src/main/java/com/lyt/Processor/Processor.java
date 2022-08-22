package com.lyt.Processor;

import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;

public interface Processor {

    /** 处理请求，给出响应
     * @param request 请求
     * @param response 响应
     */
    //调用servlet方法的地方
    void process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
