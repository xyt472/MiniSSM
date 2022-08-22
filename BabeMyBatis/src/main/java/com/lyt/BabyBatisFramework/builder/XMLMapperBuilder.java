package com.lyt.BabyBatisFramework.builder;

import com.lyt.BabyBatisFramework.config.Configuration;
import org.dom4j.Element;

import java.util.List;

/**
 * 用来专门解析映射的文件 吧读取到的xml信息 映射到configuration上
 * 目前只处理了select标签  还有insert等等
 *
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

//    public void parseMapper(Element rootElement) throws Exception {
//        //这个namespace的作用是为了去标识一个staementId
//        //statementId是由namespace +statement标签中的id值组成的  namespace是一个全局变量（这是v2版本的略）
//        String namespace = rootElement.attributeValue("namespace");   //rootElement是mapper节点
//        //可以有多个select标签   这里仅仅只是select标签
//        List<Element> Elements = rootElement.elements();
//        //长度大于0说明有这个标签
//        if (Elements.size() > 0) {
//            for (Element Element : Elements) {
//                String nodeName = Element.getName();
//                if (nodeName.equals("update") || nodeName.equals("delete") || nodeName.equals("insert")) {
//                    System.out.println("执行的是parseStatementElementV2");
//                    XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
//                    statementBuilder.parseStatementElementV2(Element, namespace);
//
//                } else {
//                    XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
//                    System.out.println("执行的是parseStatementElement");
//                    statementBuilder.parseStatementElement(Element, namespace);
//                }
//
//            }
//        }
//    }
    //这要处理很多的标签 但是这里只处理了 select 标签  其他标签需要后续慢慢去完成

    public void parseMapper(Element rootElement) throws Exception {
        //这个namespace的作用是为了去标识一个staementId
        //statementId是由namespace +statement标签中的id值组成的  namespace是一个全局变量（这是v2版本的略）
        String namespace = rootElement.attributeValue("namespace");   //rootElement是mapper节点
        //System.out.println("parseMapper中的rootElement是"+rootElement.getName());
        //可以有多个select标签   这里仅仅只是select标签
        List<Element> Elements = rootElement.elements();  //可以使用这种方式去获取子节点

        //长度大于0说明有这个标签

        if (Elements.size() > 0) {
            for (Element Element : Elements) {
                String nodeName = Element.getName();
                if (nodeName.equals("update") || nodeName.equals("delete") || nodeName.equals("insert")) {
                    //System.out.println("执行的是parseStatementElementV2");
                    XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
                    statementBuilder.parseStatementElementV2(Element, namespace);

                } else {
                    XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
                    //传来这里的有selectElement   还有resultMap标签  因为他们肯定都是一起出现的
                    if (nodeName.equals("select")) {
                      //  System.out.println("执行\"select\")");
                        statementBuilder.parseStatementElement(Element, namespace);
                    }
                    if (nodeName.equals("resultMap")) {
                       // System.out.println("执行的是resultMap");
                        statementBuilder.parseResultMap(Element);
                    }

                }
            }
        }
    }




    public void parseMapperV2(Element rootElement) throws Exception{
        //这个namespace的作用是为了去标识一个staementId
        //statementId是由namespace +statement标签中的id值组成的  namespace是一个全局变量（这是v2版本的略）
        String  namespace =rootElement.attributeValue("namespace");   //rootElement是mapper节点
        //TODO 获取动态sql 标签 比如<sql>
        //TODO 获取其他标签

        System.out.println("parseMapperV2中的rootElement是");
        //可以有多个select标签   这里仅仅只是select标签
        List<Element> selectElements=rootElement.elements("select");
       // List<Element> updateElements=rootElement.elements("update");
        //遍历获取到的标签
        for (Element selectElement : selectElements) {
            XMLStatementBuilder statementBuilder=new XMLStatementBuilder(configuration);
            statementBuilder.parseStatementElementV2(selectElement,namespace);
        }

    }
}
