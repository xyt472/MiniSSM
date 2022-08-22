package com.lyt.BabyBatisFramework.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Blog {

    private int id;
    private User author;
    private String body;
    private List<Comment> comments;
    Map<String,String>  labels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> coments) {
        this.comments = coments;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }


}
