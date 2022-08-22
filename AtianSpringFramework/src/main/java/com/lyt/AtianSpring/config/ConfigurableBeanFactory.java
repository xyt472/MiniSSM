package com.lyt.AtianSpring.config;


import com.lyt.AtianSpring.core.ConversionService;
import com.lyt.AtianSpring.factory.HierarchicalBeanFactory;
import com.lyt.AtianSpring.utils.StringValueResolver;

import java.util.List;


public interface ConfigurableBeanFactory extends SingletonBeanRegistry , HierarchicalBeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    List<BeanPostProcessor> getBeanPostProcessorS();
    /**
     * 销毁单例对象
     */
    void destroySingletons();

    /**
     * Add a String resolver for embedded values such as annotation attributes.
     * @param valueResolver the String resolver to apply to embedded values
     * @since 3.0
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     * @param value the value to resolve
     * @return the resolved value (may be the original value as-is)
     * @since 3.0
     */
    String resolveEmbeddedValue(String value);

    /**
     * Specify a Spring 3.0 ConversionService to use for converting
     * property values, as an alternative to JavaBeans PropertyEditors.
     * @since 3.0
     */
//    void setConversionService(ConversionService conversionService);
//
//    /**
//     * Return the associated ConversionService, if any.
//     * @since 3.0
//     */
//    @Nullable
    void setConversionService(ConversionService conversionService);

    ConversionService getConversionService();

}
