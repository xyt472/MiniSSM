package com.lyt.extendTest;

import java.util.Map;


public abstract  class parent {
    private Map parentMap;

    public Map getParentMap() {
       // System.out.println("在调用父类的抽象方法");
        test();
        return parentMap;
    }

    public void setParentMap(Map parentMap) {

        this.parentMap = parentMap;
    }
    public abstract void test(); //抽象方法交给子类去实现
}
