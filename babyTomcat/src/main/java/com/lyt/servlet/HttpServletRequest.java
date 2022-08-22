package com.lyt.servlet;

import com.lyt.servlet.Cookie;
import com.lyt.servlet.HttpSession;
import com.lyt.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public interface HttpServletRequest extends ServletRequest{
	public Cookie[] getCookies();
	public long getDateHeader(String name);
	public String getHeader(String name);
	//public String getMethod();
	public String getPathInfo();
	public String getPathTranslated();
	String getContextPath();
	public String getQueryString();
	public String getRequestedSessionId();
	public String getRequestURI();
	public StringBuffer getRequestURL();
	public HttpSession getSession();
	public Enumeration<String> getHeaders(String name);
	public Enumeration<String> getHeaderNames();
	public int getIntHeader(String name);
	Map<String, String> getParameterMap();
	public InputStream getInputStream();
	public String getParameter(String name);

}
