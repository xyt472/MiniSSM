package com.lyt.AtianSpringMvc.mapping;


import com.lyt.servlet.HttpServletRequest;

/**
 * 它的实现类是用来建立请求和处理类的映射关系
 * 该接口的作用是提供对该映射关系的访问，比如说根据请求查找处理类
 * HandlerMapping 是处理器映射器，其作用是根据请求的 URL 路径，通过注解或者 XML 配置，寻找匹配的处理器（Handler）信息。
 */
public interface HandlerMapping {
    // 根据请求查找处理类
    Object getHandler(HttpServletRequest request) throws  Exception;
}
