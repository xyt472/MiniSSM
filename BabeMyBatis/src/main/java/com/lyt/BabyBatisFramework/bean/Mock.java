package com.lyt.BabyBatisFramework.bean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mock {
    public static Blog newBlog(){
        Blog blog=new Blog();
        blog.setBody("test blog body");

        blog.setComments(Stream.iterate(newComment(),i->newComment()).limit(10).collect(Collectors.toList()));//???
        return blog;

    }
    public static Comment newComment(){
        Comment comment=new Comment();
        comment.setAuthor("fsdfsdfs");
        comment.setBody("sdadfeadfbody");
        comment.setUser(newUser());
        return  comment;
    }
    public static  User newUser(){
        User user=new User();
        user.setName("这是user的name");
        return user;

    }
}
