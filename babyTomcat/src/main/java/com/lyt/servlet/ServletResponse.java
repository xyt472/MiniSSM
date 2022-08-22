package com.lyt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 基本上由response去实现
 */
public interface ServletResponse {


    public void addDateHeader(String name, long date);

    public String getCharacterEncoding();
    public String getContentType();
    public String genProtocol();
    public ServletOutputStream getOutputStream() throws IOException;
    public PrintWriter getWriter() throws IOException;
    public void setCharacterEncoding(String charset);
    public void setContentLength(int len);
    int getContentLength();
    public void setContentType(String type);
    public void setContentLengthLong(long length);
}
