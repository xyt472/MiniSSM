package com.lyt.dao.pojo;

import com.lyt.BabyBatisFramework.bean.Submenu;
import lombok.Data;

import java.util.List;

@Data
public class Mainmenu {
 // Submenu submenu=new Submenu();
  private long id;
  private String title;
  private String path;
  List<Submenu> slist;

 public Mainmenu(long id, String title, String path, List<Submenu> slist) {
  this.id = id;
  this.title = title;
  this.path = path;
  this.slist = slist;
 }
}
