package com.lyt.AtianSpring.factory.test.test2;

import com.lyt.AtianSpring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;


public class test {
//  @Autowired
//  IUserService IuserService;
//@Autowired
//@Qualifier("userDao")
//private UserDao userDao;


  @Test
    public void test(){
      ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springmvc.xml");
      UserService3 userService3 = applicationContext.getBean("userService3", UserService3.class);
      UserDao userDao=(UserDao)applicationContext.getBean("userDao");
   // System.out.println("测试结果：" + userService.queryUserInfo());
    System.out.println("自动注入测试 结果：" + userDao.queryUserName("10001"));
  }

  @Test
  public void DaoTest(){
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springmvc.xml");
    UserService3 userService3 = (UserService3) applicationContext.getBean("userService3");
    userService3.testDao();
    //userService3.testDao2();
  }
  //UserService3 userService;
  @Test
  public void DaoTest2(){
//    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springmvc.xml");
//    UserService3 userService3 = (UserService3) applicationContext.getBean("userService3");
    //userService.testDao();
    //userService3.testDao2();
  }
}
