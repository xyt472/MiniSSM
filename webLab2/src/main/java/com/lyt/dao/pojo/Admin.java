package com.lyt.dao.pojo;

import lombok.Data;


//@AllArgsConstructor  //必须加这个注解 否则json转javabean时会导致某些属性为空
@Data
public class Admin {
    private String username;
    private String password;
    private String Id;
}
