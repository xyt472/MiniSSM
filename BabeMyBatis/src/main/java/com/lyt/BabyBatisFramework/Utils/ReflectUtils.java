package com.lyt.BabyBatisFramework.Utils;

/**
 * 反射对象用？？
 */
public class ReflectUtils {
    public static Class<?> resolveType(String resultType) {
        try {
            return  Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
