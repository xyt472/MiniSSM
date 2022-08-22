package com.lyt.service.serviceImpl;


import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Service;
import com.lyt.dao.mapper.AdminMapper;
import com.lyt.dao.pojo.Admin;
import com.lyt.service.LoginService;
import org.junit.Test;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin findAdminById() {
        return null;
    }

    @Override
    public boolean register(String id, String password) {
        return false;
    }

    @Override
    public Admin login(String id) {
        return null;
    }

    @Override
    public boolean login(String username,String password) {
        Admin admin=null;

        try {
             admin= adminMapper.getAdminByUsername(username);
             if(username.equals(admin.getUsername())&&password.equals(admin.getPassword())){
                 System.out.println("查询成功");
                 return true;
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(admin==null){
            System.out.println("对不起没有该用户");
            return false;
        }
        //先默认返回true 后面再实现
        return true;
    }
    @Test
    public void loginTest(){
        LoginService loginService=new LoginServiceImpl();
        loginService.login("20203776","123456");
    }
}
