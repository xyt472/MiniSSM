package com.lyt.BootStrap;

import com.lyt.Thread.ServerService;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Bootstrap类的main方法是整个Tomcat项目的入口，梳理一下就会发现，其实这个类的代码很少，
 * 也很清晰，它就是一个大Boss，指挥它的小兵Catalina类的实例完成各种操作。
 */
public class BootStrap {
    int  i=0;


    public void Run() {
        System.out.println("服务器准备启动");
        //TODO:在这里加入一个xml解析模块，读取server.xml,读取port信息
        int port = 8066;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器启动成功，监听："+serverSocket.getLocalPort()+"端口");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(port+"被占用，服务器启动失败");
            //TODO:自动选定另一个tcp的空闲端口
            System.exit(0);
        }
        while(true){
            Socket socket=null;
            try {
                //建立一个socket请求，一旦有客户端连上了进行接收
                socket = serverSocket.accept();
                System.out.println("客户端"+socket.getRemoteSocketAddress()+"联结上来了");
                //启动线程来处理这个客户端的请求与响应

                ServerService server = new ServerService(socket);  //初始化 socket 执行 父类的抽象 run方法（已经由该类重写）

                //又开启了一个线程 但是还是共享的同一个 socket对象（不同的线程共享 同一进程（程序）下的资源）

                //TODO:不要显式创建线程，使用线程池  //必须要传一个 runable对象
                Thread t = new Thread(server);  //ServerService 重写了 runable
                i++;
                System.out.println("开启线程次数 :"+i);
                t.start();  //开启线程
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("客户端"+socket.getRemoteSocketAddress()+"掉线了");
            }
        }
    }
//    private int getServerPort() {天
//        return 8888;
//    }



}
