package com.lyt.AtianSpring.factory.test.CGLibTest;

public class TestCglib {

    public static void main(String args[]) throws Exception{

            CglibProxy proxy = new CglibProxy();
            //通过生成子类的方式创建代理类
            SayHello proxyImp = (SayHello)proxy.getProxy(SayHello.class);
            //proxyImp.say();
            proxyImp.hh();

    }




}
