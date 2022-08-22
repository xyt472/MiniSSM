package com.lyt.AtianSpring.reader;


import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.registry.BeanDefinitionRegistry;
import com.lyt.AtianSpring.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * 针对XML对于的InputStream流对象进行解析
 *
 * 这里应该设置为程序一起动就开始加载
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{
    BeanDefinitionRegistry registry;
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
       // this.registry = registry;
        super(registry);
        this.registry=getRegistry();
    }

    // TODO: 2022/4/22
    public void loadBeanDefinitions(InputStream inputStream) {
        // 创建文档对象
        Document document = DocumentUtils.getDocument(inputStream);

        XmlBeanDefinitionDocumentReader documentReader = new XmlBeanDefinitionDocumentReader(registry);

        documentReader.registerBeanDefinitions(document.getRootElement());   //这里就已经扫描了
        System.out.println("在XmlBeanDefinitionReader这里已经完成了 BeanDefinitions的注册");


    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {


    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

}
