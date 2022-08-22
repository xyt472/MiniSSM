package com.lyt.dao.pojo;

import lombok.Data;

@Data
public class Easyuser {

  private long id;
  private String username;
  private String password;
  private String email;
  private String role;
  private long state;

}
