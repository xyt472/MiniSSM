package com.lyt.AtianSpring.Aop;

import com.lyt.AtianSpring.utils.ClassUtils;

/**

 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     在 TargetSource#getTargetClass 是用于获取 target 对象的接口信息的，那么这
     个 target 可能是 JDK 代理 创建也可能是 CGlib 创建，为了保证都能正确的
     获取到结果，这里需要增加判读
     ClassUtils.isCglibProxyClass(clazz)
     */

    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    /**
     * Return a target instance. Invoked immediately before the
     * AOP framework calls the "target" of an AOP method invocation.
     *
     * @return the target object, which contains the joinpoint
     * @throws Exception if the target object can't be resolved
     */
    public Object getTarget() {
        return this.target;
    }

}
