package com.lyt.BabyBatisFramework.pojo;

import lombok.Data;

import java.util.List;

@Data
public class User {
//    private int Id;
//    private String Name;
    private String sex;
    private String username;//一定要注意大小写
    private List<User> users;
}
