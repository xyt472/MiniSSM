package com.lyt.AtianSpring.reader;


import com.lyt.AtianSpring.registry.BeanDefinitionRegistry;
import org.dom4j.Element;

import java.util.List;

//针对Document整个文档进行阅读的阅读器
// 做统筹的
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {

    private BeanDefinitionRegistry registry;

    public DefaultBeanDefinitionDocumentReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void registerBeanDefinitions(Element rootElement) {
        BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(registry);

        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            String name = element.getName();
            if (name.equals("bean")){
                delegate.parseDefaultElement(element);
            }else{
                delegate.parseCustomElement(element);
            }
        }
    }

}
