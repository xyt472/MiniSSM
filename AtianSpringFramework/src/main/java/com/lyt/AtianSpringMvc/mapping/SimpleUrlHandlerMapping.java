package com.lyt.AtianSpringMvc.mapping;



import com.lyt.servlet.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * <bean  class="内置的处理器类">
 *  <props>
 *      <prop key="url">类的全路径</prop>
 *
 *  </props>
 * </bean>
 */

public class SimpleUrlHandlerMapping implements HandlerMapping{
    private Map<String,Object> urlHandlers = new HashMap<>();

    public SimpleUrlHandlerMapping() {
       // this.urlHandlers.put("/saveUser",new SaveUserHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return this.urlHandlers.get(uri);
    }
}
