package com.lyt.dao.pojo;

import lombok.Data;

@Data
public class User {

  private int id;
  private String username;
  private String password;
  private String email;
  private String role;
  private int state;

}
