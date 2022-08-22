package com.lyt.servlet;

import java.io.*;
import java.util.Collection;
import java.util.Date;


public interface HttpServletResponse extends ServletResponse {
	public void addCookie(Cookie cookie);
	//public boolean containsHeader(String name);
	//public String encodeURL(String url);
	//public String encodeRedirectURL(String url);
//	public void sendError(int sc, String msg) throws IOException;
	//public void sendError(int sc) throws IOException;
	public void sendRedirect(String location) throws IOException;
	public void sendRedirect() ;

	//	public void setDateHeader(String name, long date);
//	public void addDateHeader(String name, long date);
	public void setHeader(String name, String value);
	public void addHeader(String name, String value);
	//public void setIntHeader(String name, int value);
	//public void addIntHeader(String name, int value);
	//public void setStatus(int sc);
	//public int getStatus();
	public String getHeader(String name);

	public Collection<String> getHeaders(String name);
	public Collection<String> getHeaderNames();

}
