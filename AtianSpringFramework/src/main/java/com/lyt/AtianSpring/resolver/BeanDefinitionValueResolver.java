package com.lyt.AtianSpring.resolver;


import com.lyt.AtianSpring.config.RuntimeBeanReference;
import com.lyt.AtianSpring.config.TypedStringValue;
import com.lyt.AtianSpring.factory.BeanFactory;

/**
 * 专门负责BeanDefinition中存储的value的转换
 */
public class BeanDefinitionValueResolver {
    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resoleValue(Object value) {
        if (value instanceof TypedStringValue){
            TypedStringValue typedStringValue = (TypedStringValue) value;
            Class<?> targetType = typedStringValue.getTargetType();
            String stringValue = typedStringValue.getValue();
            if (targetType == Integer.class){
                return Integer.parseInt(stringValue);
            }else if (targetType == String.class){
                return stringValue;
            }//TODO 其他类型
        }else if (value instanceof RuntimeBeanReference){
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String ref = beanReference.getRef();
            // 递归调用
            return beanFactory.getBean(ref);
        }
        return null;
    }
}
