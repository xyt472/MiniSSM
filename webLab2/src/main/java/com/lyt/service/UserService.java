package com.lyt.service;


import com.lyt.dao.pojo.User;
import com.lyt.vo.QueryInfo;
import com.lyt.vo.UserVo;

public interface UserService {
     User getUserByName(String query) throws Exception;
     String getAllUser(QueryInfo queryInfo) throws Exception;
     int getUserCounts( ) throws Exception;
     String updateState(String id, String state) throws Exception;
     String addUser(User user);
     String getUpdateUser(String id) throws Exception;
     String editUser(User user);
     String deleteUser(String id);
}
