package com.lyt.BabyBatisFramework;

import com.lyt.BabyBatisFramework.Factory.SqlSessionFactory;
import com.lyt.BabyBatisFramework.SqlSession.SqlSession;
import com.lyt.BabyBatisFramework.builder.SqlSessionFactoryBuilder;
import com.lyt.BabyBatisFramework.pojo.User;
import org.junit.Before;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    //private DefaultSqlSessionFactory defaultSqlSessionFactory;
    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void before(){
        InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        if(inputStream!=null){
            try {
                sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream); //这个要自己去实现  不要照搬
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("读取文件失败，导致inputStream是空的");
        }
    }
    @org.junit.Test
    public void test()throws Exception{
        Map param=new HashMap();
        param.put("username","lyt");
        param.put("sex","男");
        SqlSession sqlSession= sqlSessionFactory.openSession();


//        if(sqlSession==null){
//              System.out.println("sqlSession是空的");
//          }
        List<User> users=sqlSession.selectList("test.queryUserByParams",param);
        System.out.println(users);

    }


}
