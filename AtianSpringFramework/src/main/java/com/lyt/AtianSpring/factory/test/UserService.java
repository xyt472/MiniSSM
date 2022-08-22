package com.lyt.AtianSpring.factory.test;

import com.lyt.AtianSpring.Annotation.Service;
import lombok.Data;

import java.util.Random;

@Data
public class UserService {
    private String uId;
    private String company;
    private String location;
    private UserDao userDao=new UserDao();
    //@Value("${token}")
    private String token;
  //  @Autowired
   // private UserDao userDao;
    public String queryUserInfo() {

        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao.queryUserName("10001") + "，" + token;
    }
}
