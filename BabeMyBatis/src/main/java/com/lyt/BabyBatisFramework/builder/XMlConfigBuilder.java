package com.lyt.BabyBatisFramework.builder;

import com.lyt.BabyBatisFramework.Utils.DocumentUtils;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.io.Resource;

import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 这是用来 解析全局配置文件的只new 一次
 */
public class XMlConfigBuilder {
    //注入对象
    private Configuration configuration;

    public XMlConfigBuilder() {
        configuration =new Configuration(); //初始化
    }

    public Configuration parseConfiguration(Element rootElement) {
        Element environments =rootElement.element("environments");
        parseEnvironments(environments);

        Element mappers =rootElement.element("mappers");
        try {
            parseMappers(mappers);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //  return null;  //bug出现在这里 因为他是一直返回空的
        return  configuration;
    }
    /**
     * 解析全局配置文件中的mappers标签（注意区分mapper标签） 这也是你实现sql语句的方式
     * @param  mappers  <mappers></mappers>
     */
    //这两个函数作用是读取xml的节点
    private void parseMappers(Element mappers)throws Exception {
        //这里应该用一个数组去接收所有的mapper
        List <Element> list= mappers.elements("mapper"); //获取mapper标签

        //输出一下 mapper标签
        for (Element element : list) {
            System.out.println("获取到的mapper路径 ："+ element.attributeValue("resource"));
        }

        for (Element element : list) {
            String resource =element.attributeValue("resource"); //获取 resource标签
            //根据xml的路径获取到相应的输入流
            InputStream inputStream= Resource.getResourceAsStream(resource);
            if(inputStream!=null){
                //将流对象转换成Document对象  这里为什么是null？？
                Document document= DocumentUtils.getDocument(inputStream);
                //针对Document对象 ，按照mybatis的语义去解析Document  之前一直错 就是你把Mapper写成了Mappers 这是两个不同的函数
                // parseMappers(document.getRootElement());
                //这个builder需要把解析到的东西都扔到configuration中  所以这个builder类需要依赖注入一个congfiguration


                    //todo  这里是重点！！！！！
                    XMLMapperBuilder mapperBuilder= new XMLMapperBuilder(configuration);
                    //这里需要决定去调用谁
                    mapperBuilder.parseMapper(document.getRootElement());


            }else {
                System.out.println("mappers是空的 请去检查 文件夹");
            }

        }
    }
    //TODO 重要！！！

    private void parseEnvironments(Element environments) {
        String aDefault =environments.attributeValue("default");
        List <Element> elements=environments.elements("environment");
        //遍历一下
        for (Element element : elements) {
            //得到每一个节点
            String id= element.attributeValue("id"); //得到id属性
            if(aDefault.equals(id)){
                parseDataSource(element.element("dataSource"));
            }
        }
    }

    //读取数据
    private void parseDataSource(Element dataSource) {
        String type =dataSource.attributeValue("type");
        //这应该是数据库连接池了  也可以该成 Druid
        if(type.equals("DBCP")){
            BasicDataSource ds =new BasicDataSource();
            Properties properties =parseProperties(dataSource);
            ds.setDriverClassName(properties.getProperty("db.driver"));
            ds.setUrl(properties.getProperty("db.url"));
            ds.setUsername(properties.getProperty("db.username"));
            ds.setPassword(properties.getProperty("db.password"));
            ds.setMaxActive(200);
            ds.setInitialSize(20);
            ds.setMaxIdle(100);//设置最大空闲连接数
            ds.setMinIdle(100); //设置最小空闲连接数

            configuration.setDataSource(ds);  //配置完成
            System.out.println("setDataSource配置完成");
        }
    }
    private Properties parseProperties(Element dataSource) {
        Properties properties=new Properties();
        List<Element> list= dataSource.elements("property");

        for (Element element : list) {
            String name =element.attributeValue("name");
            String value =element.attributeValue("value");
            properties.put(name,value);
        }
        return properties;
    }

}
