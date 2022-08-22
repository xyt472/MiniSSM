package com.lyt.AtianSpring.factory;

public interface FactoryBean <T>{
    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
