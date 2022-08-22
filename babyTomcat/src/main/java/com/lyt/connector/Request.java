package com.lyt.connector;

import com.lyt.servlet.Cookie;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpSession;
import com.lyt.servlet.ServletContext;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
public class Request implements HttpServletRequest{

    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求地址
     */
    private String requestURI;
    private String requestURL;
    private String Connection;
    private String jsessionid = null; //cookie的值

    private byte[] bytes;
    private int readLength;
    private  int contentLength;

    private InputStream inputStream;

    private String contentType;

    public InputStream getInputStream(){
        return this.inputStream;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 请求体
     */
    private String content;
    /**
     * 请求头
     */
    private Map<String,String> headers = new Hashtable<>();
    /**
     * 请求参数
     */
    private Map<String,String> parameterMap = new Hashtable<>();
    /**
     * 请求cookie信息
     */
    private List<Cookie> cookies = new ArrayList<>();


    public String getJsessionid() {
        return jsessionid;
    }

    //提供一个构造函数让外界使用
    public Request(InputStream inputStream) {
        //一次性读完所有请求信息
        setContent( parseContentString(inputStream));
        //解析协议
        parseProtocol();

        parseInputStream(this.readLength,this.bytes);
    }

    private String parseContentString( InputStream inputStream) {

        System.out.println("开始将byte[]转换为完整报文content");

        StringBuilder content = new StringBuilder();
        int length = -1;
        byte[] bytes = new byte[100*1024];  //100kb
        try {
            length = inputStream.read(bytes); //把所有的 数据读到这个bs中 lehgth是读入的字节数
            //保存 读取的数量
            this.readLength=length;
            //将数据保存起来
            this.bytes=bytes;
            // 里面包含了 body 因为是所有的数据都在 bs里面
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取客户请求异常");
        }

//        //设置body中的数据  （应该在解析完协议后再  设置）
       // parseInputStream(length,bytes);
        //40需要被修改
        //    InputStream inputStream2 = new ByteArrayInputStream(bytes,length-40,length);

        //将bs中的字节数据转为char  后追加到 sb当中  如果不是字节怎么办 ？这里明显有问题
        for(int i = 0;i<length;i++){
            content.append((char)bytes[i]);
        }
        if(content!=null){
          //  System.out.println("content 的值包括了body是" +content.toString());
            return content.toString();
        }

        return null;
    }

    public HttpSession getSession() {
        HttpSession session;
        //判读jsessionid是否存在，
        //不存在 ，则创建一个httpSession存起来再返回
        if(jsessionid==null|| !ServletContext.sessions.containsKey(jsessionid)) {

            jsessionid = UUID.randomUUID().toString();
            session = new HttpSession(jsessionid);
            ServletContext.sessions.put(jsessionid, session);
        } else {
            //存在则取出httpSession返回
            session = ServletContext.sessions.get(jsessionid);
        }
        return session;
    }


    /**解析协议
     * 从iis 中取出请求头，请求实体，解析数据存到属性中
     */
    private void parseProtocol() {

        System.out.println("开始解析报文");
        if(!content.equals("")){
           // System.out.println("content是 ："+content);
            String[] ss = content.split(" ");

            this.method = ss[0];
            System.out.println("method ： "+method);

            //   这里有点问题需要把参数去掉

            int index=  ss[1].indexOf("?");
            if(index>0){
                this.requestURI = ss[1].substring(0,index);

               // this.requestURL=ss[1].substring(0,index);
            }else {
                this.requestURI = ss[1];
            }
            System.out.println("++++++uri是"+this.requestURI);
          //  System.out.println("+++++++++++++++参数源 是 "+ss[1].substring(index));

            //解析参数，存到parameter的map中
            parseParameter(ss[1]);
            //解析出header存好
            //取出协议中的 Cookie：xxxx  ,如果有则说明已经生成过Cookie  没有则表明是第一次请求，要生成Cookie编号
            parseHeader();  //处理请求头
            //从headers中取cookie
            parseCookie();
            //从cookie中取出jsessionid
            jsessionid = parseJSessionId();
            //解析 请求头里其他的参数
            parseContent();

        }






    }

    public String getParameter(String key) {
        return parameterMap.get(key);
    }
    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    private void parseContent(){

        if(headers==null&&headers.size()<=0){
            return;
        }
        //String[] parts = this.content.split("\r\n");
//        if(getHeader("Content-Length")!=null){
//            int i= Integer.parseInt(getHeader("Content-Length"));
//            if(1>0){
//               
//            }
//        }
        for (String s : getHeaders().keySet()) {
            if(s.equals("Content-Length")){
                setContentLength(Integer.parseInt(getHeader("Content-Length")));
            }
            if (s.equals("host")) {
                this.requestURL="http://"+getHeader("host")+this.requestURI;

            }
        }




//        for (String part : parts) {
//            //System.out.println(" 请求体中的每一行是  ： "+ part);
//            //获取一下 Content-Length: 173
////            if(part.startsWith("Content-Length:")){
////                String [] strings= part.split(" ");
//////                System.out.println("对Content-Length进行分割 得到 值 ："+strings[1]);
////                i=Integer.parseInt(strings[1]);
////                if(i!=0){
////                    //设置 请求体数据大小
////                    setContentLength(i);
////                }
////            }
//
//            if(part.startsWith("Content-Length:")){
//                String [] strings= part.split(" ");
//            }
//
//            if(part.startsWith("Content-Length:")){
//                String [] strings= part.split(" ");
//            }
//
//        }
    }

    private String parseJSessionId() {
        if(cookies!=null&&cookies.size()>0) {
            for(Cookie c:cookies) {
                if("JSESSIONID".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /**
     * headers中取出cookie，然后在解析出cookie对象存在cookies中
     */
    private void parseCookie() {
        if(headers==null&&headers.size()<=0){
            return;
        }
        //从headers中取出键为cookie的
        String cookieValue = headers.get("Cookie");
        if(cookieValue == null || cookieValue.length()<=0) {
            return;
        }
        String[] cvs = cookieValue.split(": ");
        if(cvs.length > 0) {
            for(String cv:cvs) {
                String[] str = cv.split("=");
                if(str.length > 0) {
                    String key = str[0];
                    String value = str[1];
                    Cookie c = new Cookie(key,value);
                    cookies.add(c);
                }
            }
        }
    }
    private void parseHeader() {
        System.out.println("开始转换为请求头");
        //这是请求行
        String[] parts = this.content.split("\r\n\r\n");
//        for (String part : parts) {
//            System.out.println("parseHeader请求行： " +part);
//        }
        String[] headerss = parts[0].split("\r\n");  //这个应该是
        for(int i = 1;i<headerss.length;i++){
            String[] headPair = headerss[i].split(": ");
//            for (String s : headPair) {
//                System.out.println("parseHeader请求头的值： " +s);
//            }
            //Host: localhost:8888     Connection: keep-alive ...
            headers.put(headPair[0], headPair[1]);
        }
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    public String getHeader(String name){
        return headers.get(name);
    }

    /**
     * 取参数
     */
    private void parseParameter(String parameterSource) {
        //requestURI: user.action?name=z&password=a
        System.out.println("requestURI: "+requestURI);

        int index = parameterSource.indexOf("?");
        //有?的话
        if(index>=1){
            //截掉问号和前面的字符串  按照 &来分割
            String[] pairs = parameterSource.substring(index+1).split("&");
            for(String p:pairs){
                System.out.println("去掉问号后获得的字符串 "+p);
                String[] po = p.split("=");
                parameterMap.put(po[0], po[1]);
            }
        }else {
            this.parameterMap=null;
            System.out.println("没有参数需要处理");
        }
        //这里的代码逻辑有问题 不符合 servlet规范
//        if(this.method.equals("POST")){
//            String[] parts = this.content.split("\r\n\r\n");
//            String entity = parts[1];
//            String[] pairs = entity.split("&");
//            for(String p:pairs){
//                String[] po = p.split("=");
//                System.out.println("po lenth "+po.length);
//                parameter.put(po[0], po[1]);
//            }
//        }
    }

    private void parseInputStream( int length,byte [] bytes){

        if(length>0){
            //  int length = inputStream.read(bytes);
            System.out.println("读进数组的大小是 ：" +length);
            System.out.println("当前数据的大小是 ：" +this.contentLength);
            this.inputStream  = new ByteArrayInputStream(bytes,length-contentLength,contentLength);
//            byte [] bytes2=new byte[100*1024];
//            System.out.println("输出body中的数据");
//            int length2 = 0;
//          //  this.inputStream=inputStream.read(bytes2);
//            try {
//                length2 = inputStream.read(bytes2);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            StringBuilder sb = new StringBuilder();
//            //遍历输出 字符数组
//            for(int i = 0;i<length2;i++){
//                sb.append((char)bytes2[i]);
//            }
//            String content = sb.toString();   //将值赋给请求体
//            System.out.println("——+——+——+HttpServletRequest中的 body 数据 (请求行) "+content);
        }else {
            System.out.println("没有数据需要转换");
        }


    }
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }
    @Override
    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String name) {
        return 0;
    }

    @Override
    public Map<String, String> getParameterMap() {
        return this.parameterMap;
    }


}
