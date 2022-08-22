package com.lyt.BabyBatisFramework.sqlNode;


import com.lyt.BabyBatisFramework.Utils.GenericTokenParser;
import com.lyt.BabyBatisFramework.Utils.SimpleTypeRegistry;
import com.lyt.BabyBatisFramework.Utils.TokenHandler;
import com.lyt.BabyBatisFramework.config.DynamicContext;
import ognl.Ognl;
import ognl.OgnlException;

/**
 * 封装了带有#$的sql文本
 */
public class TextSqlNode implements SqlNode {
    private String sqlText;
    //获得sql语句
    public TextSqlNode(String sqlText){
        this.sqlText=sqlText;
    }
    //todo  有关入参1
    //有待实现
    @Override
    public void apply(DynamicContext context){
        //处理${}   使用mybatis提供的工具类即可
        GenericTokenParser tokenParser=new GenericTokenParser("${","}", new BindingTokenHandler(context));
        String sql= null;
        try {
            sql = tokenParser.parse(sqlText);
            System.out.println("经过TextSqlNode处理的sql"+sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.appendSql(sql);
    }

    public boolean isDynamic() {
        //如果包含$则是true
        return sqlText.indexOf("${")>-1;
    }
    //目的是处理${}中的内容
    public static class  BindingTokenHandler implements TokenHandler {
        //为了获取入参对象
        private DynamicContext context;
        public BindingTokenHandler(DynamicContext context) {
            this.context=context;
        }

        //${}中的参数名称
        @Override
        public String handleToken(String content) {

            System.out.println("handleToken传入的content的值为"+content);

            Object parameter =context.getBinding().get("_parameter");

            if(SimpleTypeRegistry.isSimpleType(parameter.getClass())){
                System.out.println("这是简单参数生成的sql语句int string 之类的 "+parameter.toString());
                return  parameter.toString();
            }
            //使用onglUtils 表达式获取值
//  todo 使用ongl  没有获取到value的bug出现在这里
            Object value= null;
            try {
                //原来的bug是你写成了这样  value = Ognl.getValue("content",parameter);
                //难怪你解析不出来正确的sql语句
                value = Ognl.getValue(content,parameter);
            } catch (OgnlException e) {
                e.printStackTrace();
                System.out.println("ongl出现异常");
            }
            if(value==null){
            }else {
                System.out.println("传入value的值为 ："+value.toString());
            }
            //OgnlUtils.getValue(content,parameter);
            return value==null?"":value.toString();
           // return "lyt";
        }
    }
}
