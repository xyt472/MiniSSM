package com.lyt.servlet;

import java.util.Hashtable;
import java.util.Map;

/**
 * @description: Servlet上下文
 **/
public class ServletContext {
    /**
     * servlet容器：存储servlet实例
     */
	public static Map<String,Object> servlets = new Hashtable<String,Object>();
    /**
     * 存储session信息
     */
	public static Map<String,HttpSession> sessions = new Hashtable<>();
}
