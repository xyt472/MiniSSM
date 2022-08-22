package com.lyt.connector;

import com.lyt.servlet.Cookie;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;
import com.lyt.servlet.ServletOutputStream;

import java.io.*;
import java.util.Collection;
import java.util.Date;

public class Response implements HttpServletResponse {

    private HttpServletRequest request;
    private	OutputStream outputStream;
    private String contentType="";
    private  int contentLength=0;


    private String webRoot = System.getProperty("user.dir")+ File.separator+"static";
    public Response(HttpServletRequest request, OutputStream outputStream) {
        this.request=request;
        this.outputStream = outputStream;
    }
    public PrintWriter getWriter(){
        //这是写入outputStream吗？是的  为什么可以直接写字符串？
//        try {
//            System.out.println("写入请其头");
//            this.outputStream.write(genProtocol().getBytes());  //写入请求头
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        PrintWriter printWriter=  new PrintWriter(this.outputStream);

        return printWriter;
    }



    public void sendRedirect() {
        int code = 200;
        //1.取出request中的uri
//        String uri = this.request.getRequestURI();
//        File file;
//        String fileName = null;

        byte[] bytes=new byte[100];
       // file = new File(webRoot,fileName);

        //3、存在，找到这个文件，读出来
        //4、发送文件响应，不同的文件返回不同类型

            send(bytes,"text/json",code);

    }
    /**
     * 返回响应流
     */
    private void send(byte[] bytes, String contentType, int code) {
        try {
            //先写请求头，再写入数据
            //拼接协议用
            String responseHeader = genProtocol();

         //   byte[] bs = readFile(file);

            //写入响应头
            this.outputStream.write(responseHeader.getBytes());
            this.outputStream.flush();

            //写入响应的数据
            this.outputStream.write(bytes);
            this.outputStream.flush();//使用 flush() 方法则可以强制将缓冲区中的数据写入输出流
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                this.outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件
     */
    private byte[] readFile(File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            byte[] bs = new byte[1024];
            int length;
            while((length = fis.read(bs,0,bs.length))!=-1){
                baos.write(bs, 0, length);
                baos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    /**
     * 拼接响应协议 这一块应该是要写死的吧？
     */


//       resp.setHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
//        resp.setHeader("Access-Control-Allow-Methods", "*");
//        resp.setHeader("Access-Control-Max-Age", "3600");
//        resp.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
//                + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
//        resp.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
//        resp.setHeader("Access-Control-Expose-Headers", "*");
    public String genProtocol() {
        String result = "HTTP/1.1 "+200+" OK\r\n";
        result+="Access-Control-Allow-Origin: *\r\n";
        result+="Access-Control-Allow-Credentials: true\r\n";
        result+="Access-Control-Allow-Methods: *\r\n";
        result+="Access-Control-Max-Age: 3600\r\n";
        result+="Access-Control-Allow-Headers: Authorization,Origin,X-Requested-With," +
                "Content-Type,Accept,content-Type,origin,x-requested-with," +
                "content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*\r\n";
        result+="Access-Control-Request-Headers: Authorization,Origin, X-Requested-With,content-Type,Accept\r\n";
        result+="Access-Control-Expose-Headers: *\r\n";
        result+="Content-Type: "+this.getContentType()+"\r\n";
        result+="Content-Length: "+this.getContentLength()+"\r\n";
        result+="Date: "+new Date()+"\r\n";
        result+="Connection: keep-alive\r\n";
        result+="\r\n";
        //后面还要写数据
        return result;
    }


    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public void addDateHeader(String name, long date) {

    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }



    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {
        this.contentLength=len;
    }

    @Override
    public int getContentLength() {
        return this.contentLength;
    }
    @Override
    public void setContentType(String type) {

        this.contentType=type;
    }

    @Override
    public void setContentLengthLong(long length) {

    }

    @Override
    public void sendRedirect(String location) throws IOException {

    }



    @Override
    public void setHeader(String name, String value) {

    }

    @Override
    public void addHeader(String name, String value) {

    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }
}
