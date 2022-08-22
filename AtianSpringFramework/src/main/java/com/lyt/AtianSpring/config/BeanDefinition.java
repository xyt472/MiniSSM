package com.lyt.AtianSpring.config;


public class BeanDefinition {
	// bean标签的class属性
	private String clazzName;
	// bean标签的class属性对应的Class对象
	private Class<?> clazzType;
	// bean标签的id属性和name属性都会存储到该属性值，id和name属性是二选一使用的
	private String beanName;
	private String initMethod;
	// 该信息是默认的配置，如果不配置就默认是singleton
	private String scope="singleton";

	/**
	 * bean中的属性信息
	 */
	//private List<PropertyValue> propertyValues = new ArrayList<>();
	private PropertyValues propertyValues;
	private static final String SCOPE_SINGLETON = "singleton";
	private static final String SCOPE_PROTOTYPE = "prototype";
	private boolean singleton = true;
	private boolean prototype = false;
	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}
	public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
		this.clazzType = beanClass;
		this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();

	}
	public BeanDefinition(String clazzName, String beanName) {
		this.beanName = beanName;
		this.clazzName = clazzName;
		this.clazzType = resolveClassName(clazzName);
		//this(resolveClassName(clazzName), null);
		this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
	}
	public BeanDefinition(Class clazzType) {
		//this.clazzType=clazzType;
		this(clazzType, null);
	}
	private Class<?> resolveClassName(String clazzName) {
		try {
			return Class.forName(clazzName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	private String initMethodName;

	private String destroyMethodName;
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}

//	public List<PropertyValue> getPropertyValues() {
//		return propertyValues;
//	}
//
//	public void addPropertyValue(PropertyValue propertyValue) {
//		System.out.println("已经将propertyValue注册到 类的定义中");
//		this.propertyValues.add(propertyValue);
//	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}
	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getScope() {
		return scope;
	}

//	public void setScope(String scope) {
//		this.scope = scope;
//	}

//	public void setPropertyValues(List<PropertyValue> propertyValues) {
//		this.propertyValues = propertyValues;
//	}

	public Class<?> getClazzType() {
		return clazzType;
	}
	public String getInitMethodName() {
		return initMethodName;
	}
	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public boolean isSingleton() {
		return SCOPE_SINGLETON.equals(this.scope);
	}

	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(this.scope);
	}
	public String getDestroyMethodName() {
		return destroyMethodName;
	}

	public void setDestroyMethodName(String destroyMethodName) {
		this.destroyMethodName = destroyMethodName;
	}

}
