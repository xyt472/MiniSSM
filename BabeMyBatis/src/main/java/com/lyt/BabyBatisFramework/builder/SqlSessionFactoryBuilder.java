package com.lyt.BabyBatisFramework.builder;

import com.lyt.BabyBatisFramework.Factory.DefaultSqlSessionFactory;
import com.lyt.BabyBatisFramework.Factory.SqlSessionFactory;
import com.lyt.BabyBatisFramework.Utils.DocumentUtils;
import com.lyt.BabyBatisFramework.config.Configuration;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

public class SqlSessionFactoryBuilder {
    //根据字符流获取
    public SqlSessionFactory build(InputStream inputStream){
        //获取Configuration对象
        XMlConfigBuilder configBuilder=new XMlConfigBuilder();
        Document document= DocumentUtils.getDocument(inputStream);
//        System.out.println("成功实例化 document对象  :"+document);
        //创建SqlSessionFatory对象
        //解析全局对象
        //Configuration configuration= null;
        Configuration  configuration = configBuilder.parseConfiguration(document.getRootElement());

        System.out.println("返回的就是defaultSqlSessionFactory生产的会话");
        return build(configuration);
    }
    //根据字节流
    public SqlSessionFactory build(Reader reader){
        return  null;
    }

    //为什么要这么做？
    private SqlSessionFactory build(Configuration configuration){

        DefaultSqlSessionFactory defaultSqlSessionFactory=new DefaultSqlSessionFactory(configuration);
//        if(defaultSqlSessionFactory!=null){
//            System.out.println("返回了一个defaultSqlSessionFactoryl对象 就是SqlSessionFactory");
//        }
        return defaultSqlSessionFactory;
    }

}
