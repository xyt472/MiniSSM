package com.lyt.AtianSpring.reader;



import cn.hutool.core.util.StrUtil;
import com.lyt.AtianSpring.config.*;
import com.lyt.AtianSpring.registry.BeanDefinitionRegistry;
import com.lyt.AtianSpring.utils.ReflectUtils;
import org.dom4j.Element;

import java.util.List;

public class BeanDefinitionParserDelegate {
    private BeanDefinitionRegistry registry;

    public BeanDefinitionParserDelegate(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    //todo 他并没有实现自动装配的功能  比如 autowired  我需要完成autowired注解

    //todo 实现@Componetment   service等等注解  来实现彻彻底底的自动装配
    public void parseDefaultElement(Element element) {
        System.out.println("加载bean 的参数------------------------------------------");
        String id = element.attributeValue("id");

        String clazzName = element.attributeValue("class");
        Class clazzType = ReflectUtils.resolveClassType(clazzName);
        String scope = element.attributeValue("scope");
        scope = scope == "" || scope == null ? "singleton" : scope;
        String initMethod = element.attributeValue("init-method");

        id = id == "" || id == null ? clazzType.getSimpleName() : id;
        BeanDefinition bd = new BeanDefinition(clazzName,id);
        bd.setScope(scope);
        bd.setInitMethod(initMethod);

        List<Element> elements = element.elements();
        parsePropertyElements(bd,elements);

        registry.registerBeanDefinition(id,bd);
    }

    public void parseCustomElement(Element element) {

    }

    private void parsePropertyElements(BeanDefinition bd, List<Element> elements) {
        for (Element element : elements) {
            parsePropertyElement(bd,element);
        }
    }

    private void parsePropertyElement(BeanDefinition bd, Element element) {
        String name = element.attributeValue("name");
        String value = element.attributeValue("value");
        String ref = element.attributeValue("ref");

        if (value!= "" && ref!= "" && value != null && ref != null ){
            return ;
        }
        if (value != "" && value != null){

            TypedStringValue typedStringValue = new TypedStringValue(value);

            Class targetType = ReflectUtils.resolveTargetType(bd.getClazzType(),name);
            typedStringValue.setTargetType(targetType);
            PropertyValue pv = new PropertyValue(name,typedStringValue);


            bd.getPropertyValues().addPropertyValue(pv);
        }else if(ref != "" && ref != null){
            Object value2 = StrUtil.isNotEmpty(ref) ? new BeanReference(ref) : value;
          //  RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            PropertyValue pv = new PropertyValue(name,value2);
            System.out.println("+******+++++++++++已经为BeanDefinition注册 PropertyValue");
            bd.getPropertyValues().addPropertyValue(pv);
        }
    }


}
