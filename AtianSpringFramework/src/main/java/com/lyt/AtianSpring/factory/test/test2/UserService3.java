package com.lyt.AtianSpring.factory.test.test2;

import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Service;


import java.util.Random;

@Service
public class UserService3 {
//    @Value("${token}")
//    private String token;
    @Autowired
    DaoTEST daoTEST;
    @Autowired
    UserDao userDao;
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // return userDao.queryUserName("10001") + "，" ;
        //return "小傅哥，100001，深圳，";
        return  null;
    }

    public void testDao(){
        daoTEST.test("2021",0);
    }

    public String register(String userName) {
        return "注册用户：" + userName + " success！";
    }

    public void testDao2(){
        userDao.queryUserName("10001");
    }

}
