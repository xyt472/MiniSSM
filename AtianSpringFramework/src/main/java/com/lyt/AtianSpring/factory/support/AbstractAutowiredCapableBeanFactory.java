package com.lyt.AtianSpring.factory.support;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.lyt.AtianSpring.config.*;
import com.lyt.AtianSpring.factory.*;
import com.lyt.AtianSpring.init.InitializingBean;
import com.lyt.AtianSpring.utils.ReflectUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 完成Bean的创建和依赖装配
 */
public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory implements AutowiredCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
    //主要核心
    @Override
    public Object createBean(BeanDefinition bd,Object[] args) {
//        for (PropertyValue propertyValue : bd.getPropertyValues()) {
//            System.out.println("遍历输出 一下当前类定义中的 propertyValue name :"+propertyValue.getName());
//            System.out.println("遍历输出 一下当前类定义中的 propertyValue value :"+propertyValue.getValue());
//        }
        Object bean = null;
        // 判断是否返回的是代理 Bean 对象
        bean = resolveBeforeInstantiation(bd.getBeanName(), bd);
        if (null != bean) {
            //
            return bean;
        }

        // 1.Bean的实例化  这里是靠构造函数创建  还可以通过 setter方法 注解等
         bean = createBeanInstance(bd,args);
        // 实例化后判断   是不是后置处理器  是的话直接返回
        //比如AutowiredAnnotationBeanPostProcessor 这种类就会直接返回 后面不再执行
        //这个类在项目一启动的时候  类定义就会被加载的容器当中   后置处理器在 实例化之前完成注册
        boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(bd.getBeanName(), bean);

        if (!continueWithPropertyPopulation) {
            System.out.println("执行了continueWithPropertyPopulation  直接返回了bean  没有进行 属性注入");
            return bean;
        }
        // 在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值  增加属性值
        //主要是用来配合 自动注入 @autowired 使用  类的定义中的PropertyValue已经被修改（AutowiredAnnotationBeanPostProcessor）
        applyBeanPostProcessorsBeforeApplyingPropertyValues(bd.getBeanName(),bean,bd);


        // 2.Bean的属性填充（依赖注入）
        // TODO 处理循环依赖(相当于applyPropertyValues(beanName, bean, beanDefinition);)
        populateBean(bean,bd);  //没有值 的话就不需要填充了
        // 3.Bean的初始化  做了一些增强处理  更加具备扩展性  aop的入口函数   后面要搞要一个    BeanPostProcessor方法
         bean= initilizeBean(bean,bd);

        // 注册实现了 DisposableBean 接口的 Bean 对象
        registerDisposableBeanIfNecessary(bd.getBeanName(), bean, bd);
        // 判断 SCOPE_SINGLETON、SCOPE_PROTOTYPE
        if (bd.isSingleton()) {
          //  System.out.println("createBean中bd.getBeanName()："+bd.getBeanName()+"  bd的值");
            registerSingleton(bd.getBeanName(), bean);
        }

       // System.out.println("返回了 被实例化 初始化后的bean bean "  +bean.getClass().getName());
        return bean;
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getClazzType(), beanName);
        if (null != bean) {
            System.out.println("创建的是代理类对象");
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) return result;
            }
        }
        return null;
    }
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非 Singleton 类型的 Bean 不执行销毁方法
        if (!beanDefinition.isSingleton()) return;
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
    //这个类的方法非常强大 ，主要是在创建类之前，做一些预处理  还有 接入处理  后面还可能需要整合mybatis
    private Object initilizeBean(Object bean, BeanDefinition beanDefinition) {

     //   System.out.println("当前bean的类型是  "+bean.getClass().getName());
        if (bean instanceof Aware){
            if (bean instanceof BeanFactoryAware){
             //   System.out.println("11111111正在设置 bean 类型是 ："+bean.getClass().getName());
              //  System.out.println("初始化为 了 当前beanfactroy ");
                ((BeanFactoryAware)bean).setBeanFactory(this);
                //((BeanFactoryAware)bean)
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
        }
        // 需要针对Aware接口标记的类进行特殊处理
        //当开始注册RequestMappingHandlerMapping的时候就开始执行
//        if (bean instanceof InitializingBean){
//            //todo 这里和 整合mvc有关
//            //这里会完成一些列的对于uri的操作   把设置好参数 和返回值类型的 handler放入到 urlHandlers中
//          //  System.out.println("~~《》~~《》~~~~《》~~~~~~《》~~~~~~开始设置 springMvc 类型是 ："+bean.getClass().getName());
//            System.out.println("实现了InitializingBean接口的 才可以 放入mvc 中 比如 RequestMappingHandlerMapping");
//            //他会获取到所有controller
//             ((InitializingBean)bean).afterPropertiesSet();
//        }
        // 可以进行IntilizingBean接口的处理  InitializingBean其实是起一个标签的作用 标识实现了该接口的类
        //RequestMappingHandlerMapping就实现了这个接口 所以他会被标识

        //对这个bean进一步处理  ，前提是xml中加了InitMethod  不然这一步不起任何的作用。 扫描机制里面也可以尝试加一加
        invokeInitMethod(bean,beanDefinition);

        // 1. 执行 BeanPostProcessor Before 处理

        //todo bean 实例化的问题很可能是出现在了这里 因为返回的是null    对bean进一步处理 有没有感觉问题不大
        //主要其作用的还是 开发者去重写BeanPostProcessor  这个类  然后重写 postProcessBeforeInitialization方法才起作用
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanDefinition.getBeanName());

        // 执行 Bean 对象的初始化方法
        try {
            invokeInitMethods(beanDefinition.getBeanName(), wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean["  + "] failed", e);
        }
        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanDefinition.getBeanName());
        return wrappedBean;

    }
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1. 实现接口 InitializingBean
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2. 注解配置 init-method {判断是为了避免二次执行销毁}
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getClazzType().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }
    private void invokeInitMethod(Object bean, BeanDefinition beanDefinition) {
        try {
            String initMethod = beanDefinition.getInitMethod();
            if (initMethod == null) {
                return;
            }
            Class<?> clazzType = beanDefinition.getClazzType();
            //通过反射 对类中的方法进行调用
            ReflectUtils.invokeMethod(bean,initMethod);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        //使用自动注入的话 是读取不到配置信息的吧 ？
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        // PropertyValues propertyValues = beanDefinition.getPropertyValues();
        //如果propertyValues是空的 就不会注入了  因为 你使用了 autowired 那么propertyValues自然就为空了
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                // A 依赖 B，获取 B 的实例化
                BeanReference beanReference = (BeanReference) value;
                //根据名称去容器里寻找bean
                value = getBean(beanReference.getBeanName());
            }
            // 属性填充
            BeanUtil.setFieldValue(bean, name, value);
        }
    }
    //使用无参构造器穿建实例
    private Object createBeanInstance(BeanDefinition beanDefinition,Object [] args) {

        Constructor constructorToUse = null;
        // TODO 静态工厂方法、工厂实例方法
        // 构造器方式去创建Bean实例
        Class<?> clazzType = beanDefinition.getClazzType();
        //获得当前类的所有的构造函数
        Constructor<?>[] declaredConstructors = clazzType.getDeclaredConstructors();
        for (Constructor ctor : declaredConstructors) {
            //这里仅仅是根据参数的数量来判断 构造器的类型  这里仅仅只是在选择构造器而已
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
                //todo 这里开始和自动注入有关了    这里开始使用构造器  使用的是cglib的来实例化

        Object object=  getInstantiationStrategy().instantiate(beanDefinition, beanDefinition.getBeanName(), constructorToUse, args);

        return object;
        // return ReflectUtils.createBean(clazzType);
    }
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        //遍历所有的 BeanPostProcessor
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            //找到重写了 InstantiationAwareBeanPostProcessor  那个AutowiredAnnotationBeanPostProcessor就重写了
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                //这里真正的处理了 注解   利用注解里面的信息  直接注入修改的对象
                //处理当前bean 的属性值 完成自动注入
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);

                if (null != propertyValues) {
                    for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
//                        System.out.println(" 这是 通过注解 注入的对象  现在通过读取注解 如@value 等等 为类定义增加 propertyValue");
                       // beanDefinition.getPropertyValues().add(propertyValue);  这是错误用法
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);

                    }
                }
            }
        }

    }
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }
    /**
     * Bean 实例化后对于返回 false 的对象，不在执行后续设置 Bean 对象属性的操作
     *
     * @param beanName
     * @param bean
     * @return
     */
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        //获取  已经完成注册的  beanPostProcessor
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

        for (BeanPostProcessor processor : getBeanPostProcessors()) {

            //只有是自定义的类 才有用 其他都是直接返回 bean 不做任何处理
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

}
