package com.lyt.Thread;

import com.lyt.Processor.DynamicProcessor;
import com.lyt.Processor.Processor;
import com.lyt.Processor.StaticProcessor;
import com.lyt.connector.Request;
import com.lyt.connector.Response;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerService implements Runnable{
    private Socket socket;

    public ServerService(Socket socket) {
        super();  //Run()是父类的抽象方法 在这里调用了run 其实就会调用重写到的 run方法
        this.socket = socket;
    }


    @Override
    public void run() {

        InputStream inputStream;
        OutputStream outputStream;
        try {
            //分别实例化 输入输出流对象
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端"+this.socket.getRemoteSocketAddress()+"掉线");
            return;
        }
        //http是一个基于请求与响应的协议
        //1、创建一个请求对象                      //我要怎么去创建呢？我需要一个包装类？
        HttpServletRequest request = new Request(inputStream);  //对request进行包装   这里理论上应该使用的是 源码里的那个request

        //2、创建一个响应对象
        HttpServletResponse response = new Response(request, outputStream); //对reponse进行包装

        String uri = request.getRequestURI(); //获取访问的地址  这里应该是包含了 参数 信息的 但是并没有实现完整分割？
        System.out.println("——+——+——+——打印一下uri");
        if(uri!=null){

            System.out.println("+++++++++++++++开始处理请求");
            System.out.println("uri "+uri);
            //判断是动态的还是静态的请求
            Processor processor=new DynamicProcessor(); //返回了 response对象（执行完servlet后返回）

            //在这里开始调用servlet
            try {
                processor.process(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //todo 应该在这里增加 一个长连接 判断 keep alive
        }

        try {
            //http是无状态的短连接    可是我想实现长连接啊？
            System.out.println("关闭socket");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
