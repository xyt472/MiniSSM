package com.lyt.BabyBatisFramework.bean;

import lombok.Data;

@Data
public class Comment {
    private String author;
    private User user;
    private  String body;
}
