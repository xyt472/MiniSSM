package com.lyt.AtianSpring.context.event;

import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.context.ApplicationEvent;
import com.lyt.AtianSpring.context.ApplicationListener;
import com.lyt.AtianSpring.factory.BeanFactory;

/**
 * Simple implementation of the {@link ApplicationEventMulticaster} interface.
 * <p>

 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }
}
