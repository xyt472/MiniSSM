package com.lyt.AtianSpringMvc.adapter;



import com.lyt.AtianSpringMvc.model.ModelAndView;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;


/**
 * 该接口是DispatcherServlet用来调用不同类型处理器的统一类型
 * HandlerAdapter和Handler类型的一对一的，就类似于某一个类型的电脑，对应一个统一的电源适配器
 * HandlerAdapter 是处理器适配器，其作用是根据映射器找到的处理器（Handler）信息，按照特定规则执行相关的处理器（Handler）。
 */
public interface HandlerAdapter {

    /**
     * 用来匹配适配器和处理器
     * @param handler
     * @return
     */
    boolean supports(Object handler);

    /**
     * 调用不同类型的处理器完成请求处理
     * @param handler    带有controller注解的 类下面的 带有requestMapping注解的方法
     * @param request
     * @param response
     * @return
     */
    ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
