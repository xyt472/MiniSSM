package com.lyt.BabyBatisFramework.Utils;


import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import java.math.BigDecimal;

//这个工具类我也不会啊！！！
public class OgnlUtils {

    public static Object getValue(String expression, Object paramObject) throws OgnlException {
        OgnlContext context=new OgnlContext();
        context.setRoot(paramObject);

        Object OgnlExpression = Ognl.parseExpression(expression);
        Object value =Ognl.getValue(OgnlExpression,context,context.getRoot());
        return value;

    }

    /**
     * 通过Ognl表达式 ，去计算boolean类型的结果
     * @param expression
     * @param parameterObject
     * @return
     * @throws Exception
     */

    public boolean evaluateBoolean(String expression, Object parameterObject)throws Exception{
        Object value = OgnlUtils.getValue(expression, parameterObject);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.ZERO) != 0;
        }
        return value != null;
    }

}
