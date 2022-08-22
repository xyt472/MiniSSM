package com.lyt.AtianSpring.Aop;

import com.lyt.AtianSpring.Aop.framework.AopProxy;
import com.lyt.AtianSpring.Aop.framework.Cglib2AopProxy;
import com.lyt.AtianSpring.Aop.framework.JdkDynamicAopProxy;


public class ProxyFactory {

    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }

        return new JdkDynamicAopProxy(advisedSupport);
    }

}
