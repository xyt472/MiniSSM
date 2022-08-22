package com.lyt.AtianSpring.factory;

import com.lyt.AtianSpring.config.*;
import com.lyt.AtianSpring.resource.ClasspathResource;
import com.lyt.AtianSpring.resource.Resource;
import com.lyt.AtianSpring.utils.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * 占位符的处理
 *  依赖于 BeanFactoryPostProcessor 在 Bean 生命周期的属性，可以在 Bean 对象
 * 实例化之前，改变属性信息。所以这里通过实现 BeanFactoryPostProcessor 接
 * 口，完成对配置文件的加载以及摘取占位符中的在属性文件里的配置。
 *  这样就可以把提取到的配置信息放置到属性配置中了，
 * buffer.replace(startIdx, stopIdx + 1, propVal);
 * propertyValues.addPropertyValue
 */
public class PropertyPlaceholderConfigurer  implements BeanFactoryPostProcessor {
    /**
     * Default placeholder prefix: {@value} 前缀
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    /**
     * Default placeholder suffix: {@value}  后缀
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;
    private String resolvePlaceholder(String value, Properties properties) {
        String strVal = value;
        StringBuilder buffer = new StringBuilder(strVal);
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }
        return buffer.toString();
    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            // 加载属性文件
            //这个资源文件你是如何·理解的？
        try {
           // DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
           // Resource resource = resourceLoader.getResource(location);  //本质上是为了获得inputstream
            Resource resource=new ClasspathResource(location);  //这里填的是Properties的路径
            Properties properties = new Properties();
            //与原版有一定出入
            properties.load(resource.getResource());
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();


            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues())
                {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) continue;
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
                        String propKey = strVal.substring(startIdx + 2, stopIdx);
                        String propVal = properties.getProperty(propKey);
                        buffer.replace(startIdx, stopIdx + 1, propVal);
                        //propertyValues.add(new PropertyValue(propertyValue.getName(), buffer.toString()));

                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));



                    } } }
            // 向容器中添加字符串解析器，供解析@Value 注解使用
            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);

            /**解析属性配置的类 PropertyPlaceholderConfigurer 中，最主要的其实就是这行代码的操作
             * beanFactory.addEmbeddedValueResolver(valueResolver) 这是把
             * 属性值写入到了 AbstractBeanFactory 的 embeddedValueResolvers 中。
             *  这里说明下，embeddedValueResolvers 是 AbstractBeanFactory 类新增加的集合
             * List<StringValueResolver> embeddedValueResolvers String
             * resolvers to apply e.g. to annotation attribute values
             */
            beanFactory.addEmbeddedValueResolver(valueResolver);
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }

    }
    public class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        private final Properties properties;
        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }
        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }
    }
}
