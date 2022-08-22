package com.lyt.controller;

import com.lyt.AtianSpring.Annotation.Autowired;
import com.lyt.AtianSpring.Annotation.Controller;
import com.lyt.AtianSpringMvc.annotation.RequestBody;
import com.lyt.AtianSpringMvc.annotation.RequestMapping;
import com.lyt.AtianSpringMvc.annotation.ResponseBody;

import com.lyt.dao.pojo.User;
import com.lyt.service.LoginService;
import com.lyt.service.UserService;
import com.lyt.service.serviceImpl.UserServiceImpl;
import com.lyt.vo.UserVo;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping("login")
    @ResponseBody
    public User login(@RequestBody User user)throws Exception{
    return userService.getUserByName(user.getUsername());
    }

}
