package com.lyt.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Service;

import com.lyt.dao.mapper.UserMapper;
import com.lyt.dao.pojo.User;
import com.lyt.service.UserService;
import com.lyt.vo.QueryInfo;


import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    //注入mapper
   // private UserMapper userMapper= DefaultSqlSession.getMapper(UserMapper.class);

    @Autowired
    UserMapper userMapper;
    @Override
    public User getUserByName(String queryName) throws Exception {
//        HashMap<String,Object> res=new HashMap<>();
//        int numbers=7;  //这个因为没有走缓存导致了卡顿？？
//      List<User> user  =userMapper.getUserByName(queryName);
//        res.put("numbers",numbers);
//        res.put("data",user);
//        System.out.println("总条数："+numbers);
//        String productJsonString = JSON.toJSONString(res);
//       // String productJsonString= JSON.toJSONString(user);
//        UserVo userVo=new UserVo();
//        userVo.setUser(userMapper.getUserByName(queryName));
//        userVo.setId(userMapper.getUserByName(queryName).getId());
        if(userMapper.getUserByName(queryName)!=null){
            return userMapper.getUserByName(queryName);
        }

        return null ;
    }

    @Override
    public int getUserCounts() throws Exception {
        int n=userMapper.getUserCounts();
        return n;
    }

    @Override
    public String updateState(String id, String state) throws Exception{
        int n=userMapper.getUserCounts();
        String str = n >0?"success":"error";
        return str;
    }

    @Override
    public String getAllUser(QueryInfo queryInfo) throws Exception {
        HashMap<String,Object> res=new HashMap<>();
        // int numbers=userMapper.getProductCounts();
        int numbers=7;  //这个因为没有走缓存导致了卡顿？？
        List<User> userList=userMapper.getAllUser(queryInfo);
        res.put("numbers",numbers);
        res.put("data",userList);
        System.out.println("总条数："+numbers);
        String productsJson = JSON.toJSONString(res);
        return productsJson;
    }
//    @Override
//    public String updateState(String id, int state) {
//        int i=ProductMapper.updateState(id,state);
//        System.out.println("用户编号:"+id);
//        System.out.println("用户状态:"+state);
//        String str = i >0?"success":"error";
//        return str;
//    }

    @Override
    public String addUser(User user) {

        int i = userMapper.addUser(user);
        if(i>0){
            System.out.println("插入product成功");
        }
        String str = i >0?"success":"error";
        return str;
    }

    @Override
    public String getUpdateUser(String id) throws Exception {

        User user =userMapper.getUpdateUser(id);
        String users_json = JSON.toJSONString(user);
        return users_json;
    }

    @Override
    public String editUser(User user) {
        int i = userMapper.editUser(user);
        String str = i >0?"success":"error";
        return str;
    }

    @Override
    public String deleteUser(String id) {

        System.out.println(id);
        int i =userMapper.deleteUser(id);
        String str = i >0?"success":"error";
        return str;
    }
}
