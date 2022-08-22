package com.lyt.BabyBatisFramework.Utils;
import com.lyt.BabyBatisFramework.Factory.SqlSessionFactory;
import com.lyt.BabyBatisFramework.builder.SqlSessionFactoryBuilder;
import com.lyt.BabyBatisFramework.io.Resource;
import lombok.Data;

import java.io.InputStream;

@Data
public class BaseDaoPlus {
    public static SqlSessionFactory sqlSessionFactory;
    //自动初始化
    static {
        System.out.println("项目启动自动执行====================================================");
        String location ="mybatis-config.xml";
        //InputStream is=Resource.getResourceAsStream(location);
        InputStream inputStream= Resource.getResourceAsStream(location);
        //创建sqlSessionFactory
        if(inputStream!=null){
            try {
                sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
                System.out.println("已经加载好文件  -------------------------------------------------------");
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("读取文件失败，导致inputStream是空的");
        }
    }

}
