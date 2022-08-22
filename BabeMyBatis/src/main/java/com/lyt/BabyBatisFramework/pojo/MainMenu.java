package com.lyt.BabyBatisFramework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MainMenu {
    private int id;
    private String title;
    private String path;
    List<SubMenu> slist;
}
