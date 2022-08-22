package com.lyt.vo;

import com.lyt.AtianSpring.Annotation.DAO;
import com.lyt.dao.pojo.User;
import lombok.Data;

@Data
public class UserVo {
    User user;
    int id;
}
