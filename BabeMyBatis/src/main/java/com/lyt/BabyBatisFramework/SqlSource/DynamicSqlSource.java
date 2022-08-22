package com.lyt.BabyBatisFramework.SqlSource;


import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.DynamicContext;
import com.lyt.BabyBatisFramework.parser.SqlSourceParser;
import com.lyt.BabyBatisFramework.sqlNode.SqlNode;

/**
 * 封装了$#和动态标签的sql信息 ，并提供对他们的处理接口
 * 每一次处理$#或者动态标签 ，都要根据入参对象，重新去生成新的sql语句，
 *  所以说每一次都要重新调用getBoundSql方法
 *  例如 select *from user where username=${id}  id是变量  每一次传值，id都可能会改变
 */
public class DynamicSqlSource implements SqlSource{
    //这是一颗树 封装了带有$#或者动态标签的 sql脚本 （树状结构）
    private SqlNode mixedSqlNode;

    public DynamicSqlSource(SqlNode mixedSqlNode) {
        this.mixedSqlNode = mixedSqlNode;
    }

    //这是处理流程  这个需要我自己去实现吧  这个函数的目的就是为了的到sql语句
    @Override
    public BoundSql getBoundSql(Object param) {
        //真正处理#$的逻辑要放在该构造方法中
        //吧处理之后的结果封装成一个静态的SqlSource（StaticSqlSource）

        //1处理所有的sql节点 ，获取合并之后的完整的sql语句，可能还带有 #{}
        //问题出现在这里 object默认居然是空的！！是因为这是静态sql
       // DynamicContext context=new DynamicContext(null); 抄的时候的写法
        DynamicContext context=new DynamicContext(param);
        try {
            mixedSqlNode.apply(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sqlText= context.getSql();  //获得完整的sql语句
        System.out.println("读取到的sql语句  它把应该把username替换为lyt的但是却没有"+sqlText);

        //2调用sqlsource的处理逻辑 。对于#{}进行处理
        //可能还会有#{}   原理和TextSqlNode差不多 吧$换成#即可
//        PareameterMappingTokenHandler tokenHandler=new PareameterMappingTokenHandler();
//        GenericTokenParser tokenParser=new GenericTokenParser("#{","}",(TokenHandler) new TextSqlNode.BindingTokenHandler(context));
//        String sql= tokenParser.parse(sqlText);
        SqlSourceParser parser=new SqlSourceParser();
        SqlSource staticSqlSource= null;
        try {
            staticSqlSource = parser.parse(sqlText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //sqlSource =new StaticSqlSource(sql, )
        //return  new BoundSql(sql,tokenParser.get)
        try {
            return staticSqlSource.getBoundSql(param);  //最终是返回了 staticSqlSource
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
