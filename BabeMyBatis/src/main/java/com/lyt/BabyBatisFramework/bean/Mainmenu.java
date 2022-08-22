package com.lyt.BabyBatisFramework.bean;

import java.util.List;


public class Mainmenu {
 // Submenu submenu=new Submenu();
  private long id;
  private String title;
  private String path;
  List<Submenu> slist;
 //List<Object> slist;

 public long getId() {
  return id;
 }

 public void setId(long id) {
  this.id = id;
 }

 public String getTitle() {
  return title;
 }

 public void setTitle(String title) {
  this.title = title;
 }

 public String getPath() {
  return path;
 }

 public void setPath(String path) {
  this.path = path;
 }

 public List<Submenu> getSlist() {
  return slist;
 }

 public void setSlist(List<Submenu> slist) {
  this.slist = slist;
 }

 @Override
 public String toString() {
  return "Mainmenu{" +
          "id=" + id +
          ", title='" + title + '\'' +
          ", path='" + path + '\'' +
          ", slist=" + slist +
          '}';
 }
}
