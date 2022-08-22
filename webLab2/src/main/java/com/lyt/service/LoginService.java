package com.lyt.service;

import com.lyt.dao.pojo.Admin;

public interface LoginService {
    //Admin findAdminById();
    boolean register(String id,String password);
     Admin login(String id);
    Admin findAdminById();
    boolean login(String id, String password);


}
