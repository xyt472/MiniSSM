package com.lyt.dao.mapper;

import com.lyt.AtianSpring.Annotation.DAO;
import com.lyt.BabyBatisFramework.Annotation.Delete;
import com.lyt.BabyBatisFramework.Annotation.Param;
import com.lyt.BabyBatisFramework.Annotation.Select;
import com.lyt.BabyBatisFramework.Annotation.Update;

import com.lyt.dao.pojo.User;
import com.lyt.vo.QueryInfo;

import java.util.List;
@DAO
public interface UserMapper {

    @Delete("test.deleteUser")
     int deleteUser(@Param("id") String id);

    @Select(statementId = "test.getUserCounts",type = "selectCount")
     Integer getUserCounts() throws Exception;

    @Select(statementId = "test.getUserByName",type = "selectOne")
     User getUserByName(@Param("queryName") String queryName) throws Exception;

    @Select(statementId = "test.getAllUser",type = "selectList")
     List<User> getAllUser(QueryInfo queryInfo) throws Exception;

    @Update("test.updateState")
     int updateState(@Param("id") String id, @Param("state") int state);

    @Update("test.addUser")
     int addUser(User user);

    @Select(statementId = "test.getUpdateUser",type = "selectOne")
     User getUpdateUser(@Param("id") String id) throws Exception;

    @Update("test.editUser")
     int editUser(User user);

}
