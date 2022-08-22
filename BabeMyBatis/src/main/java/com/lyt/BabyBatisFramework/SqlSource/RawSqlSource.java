package com.lyt.BabyBatisFramework.SqlSource;


import com.lyt.BabyBatisFramework.config.BoundSql;
import com.lyt.BabyBatisFramework.config.DynamicContext;
import com.lyt.BabyBatisFramework.parser.SqlSourceParser;
import com.lyt.BabyBatisFramework.sqlNode.SqlNode;

/**
 * 封装了不带有$#和动态标签的sql信息 ，并提供对他们的处理接口
 * 注意事项 ：
 * 只有处理$#时 ，只需要一次调用getBoundSql方法，就可以获取对应的sql语句 所以是静态的
 * 例如  例如 select *from user where username=#{}
 *  生成的效果是一个带问号的 sql语句select *from user where username=？
 */
public class RawSqlSource implements SqlSource{
    //一个静态的sqlSource（StaticSqlSource）

    private SqlSource sqlSource;

    public RawSqlSource(SqlNode mixedSqlNode) {
        //真正处理#$的逻辑要放在该构造方法中

        //吧处理之后的结果封装成一个静态的SqlSource（StaticSqlSource）

        //1处理所有的sql节点 ，获取合并之后的完整的sql语句，可能还带有 #{}
        DynamicContext context=new DynamicContext(null);
        try {
            mixedSqlNode.apply(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sqlText= context.getSql();  //获得完整的sql语句

      //  System.out.println("RawSqlSource中的 sqlText："+sqlText);
        //输出  select *from user where sex =#{sex}
        //但是xml中是这样写的 select * from user where sex=#{sex} and username like '%${username}';
        //这里绝对有问题
        //2调用sqlsource的处理逻辑 。对于#{}进行处理
        //可能还会有#{}   原理和TextSqlNode差不多 吧$换成#即可
        SqlSourceParser parser=new SqlSourceParser();
        sqlSource= parser.parse(sqlText);
        //sqlSource =new StaticSqlSource(sql, )
    }
    //这是处理流程
    @Override
    public BoundSql getBoundSql(Object param)throws Exception {
        return sqlSource.getBoundSql(param);
    }

}
