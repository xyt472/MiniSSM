package com.lyt.AtianSpring.Aop;


import com.lyt.AtianSpring.Annotation.Component;
import com.lyt.AtianSpring.Aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.lyt.AtianSpring.config.BeansException;
import com.lyt.AtianSpring.config.InstantiationAwareBeanPostProcessor;
import com.lyt.AtianSpring.config.PropertyValues;
import com.lyt.AtianSpring.factory.BeanFactory;
import com.lyt.AtianSpring.factory.BeanFactoryAware;
import com.lyt.AtianSpring.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;


import java.util.Collection;

/**
 * BeanPostProcessor implementation that creates AOP proxies based on all candidate
 * Advisors in the current BeanFactory. This class is completely generic; it contains
 * no special code to handle any particular aspects, such as pooling aspects.

 */
@Component
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {


    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {

        return pvs;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
       //判断 如果是
        if (isInfrastructureClass(bean.getClass())) return bean;

        if(beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class)==null){
            System.out.println("++————————++++——————没有获取到 AspectJExpressionPointcutAdvisor ");
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            // 返回代理对象
            System.out.println("+++++++++++++++++++++++++++++++返回了代理类对象");
            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }


    
}
