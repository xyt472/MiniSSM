package com.lyt.dao.pojo;

import lombok.Data;

@Data
public class Menu {

  private long id;
  private String menuCode;//菜单编码',
  private String menuName;//菜单名字
  private String menuLevel;//菜单级别',
  private String menuParentCode;// '菜单的父code'
  private String menuClick;//'点击触发的函数',
  private String menuRight;//表示学员，1表示老师，0管理员，可以用逗号组合使用',
}
