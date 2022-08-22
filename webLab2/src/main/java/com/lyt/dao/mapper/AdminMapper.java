package com.lyt.dao.mapper;

import com.lyt.AtianSpring.Annotation.DAO;
import com.lyt.BabyBatisFramework.Annotation.Insert;
import com.lyt.BabyBatisFramework.Annotation.Select;
import com.lyt.dao.pojo.Admin;


import java.util.List;
@DAO
public interface AdminMapper {

    Admin getAdminByUsername(String username) ;

    @Select(statementId = "",type = "")
    int isExistAdmin(Admin admin);

    @Select(statementId = "",type = "")
    Admin getAdminByUsername();

    @Select(statementId = "",type = "")
    List<Admin> getAllAdmin();

    @Insert("")
    int insertAdmin(Admin admin);


}
