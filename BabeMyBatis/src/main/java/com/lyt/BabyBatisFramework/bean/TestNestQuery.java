package com.lyt.BabyBatisFramework.bean;

import com.lyt.BabyBatisFramework.Factory.SqlSessionFactory;
import com.lyt.BabyBatisFramework.SqlSession.SqlSession;
import com.lyt.BabyBatisFramework.builder.SqlSessionFactoryBuilder;
import com.lyt.BabyBatisFramework.io.Resource;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestNestQuery {

    //private DefaultSqlSessionFactory defaultSqlSessionFactory;
    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void before(){
        String location ="mybatis-config.xml";
        //InputStream is=Resource.getResourceAsStream(location);
        InputStream inputStream= Resource.getResourceAsStream(location);
        //创建sqlSessionFactory
        if(inputStream!=null){
            try {
                sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream); //这个要自己去实现  不要照搬
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("读取文件失败，导致inputStream是空的");
        }
    }
    @Test
    public void test()throws Exception{
        Map param=new HashMap();
        param.put("username","lyt");
        param.put("sex","男");
        SqlSession sqlSession= sqlSessionFactory.openSession();
        if(sqlSession==null){
            System.out.println("sqlSession是空的");
        }
        List< User > users=sqlSession.selectList("test.queryUserByParams2",param);
        System.out.println(users);

    }
}
