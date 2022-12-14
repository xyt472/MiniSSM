package com.lyt.servlet;

public class Cookie {
    private static final long serialVersionUID = -3977915365961941126L;
    /**
     * cookie的名称
     */
    private String name;
    /**
     * cookie的值
     */
    private String value;
    //cookie还有path  domin  maxAge等属性，在此简化使用

    @Override
    public String toString() {
        return "Cookie [name=" + name + ", value=" + value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public Cookie(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }
}
