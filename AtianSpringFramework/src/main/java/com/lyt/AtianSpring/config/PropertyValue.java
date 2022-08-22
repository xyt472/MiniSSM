package com.lyt.AtianSpring.config;

/**
 * PropertyValue就封装着一个property标签的信息
 */
public class PropertyValue {
	
	private String name;

	private Object value;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}

}
