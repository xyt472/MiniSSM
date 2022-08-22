package com.lyt.AtianSpring.factory.test.test2;

import com.lyt.AtianSpring.Annotation.Component;

@Component("IuserService")
public interface IUserService {
    String queryUserInfo();

    String register(String userName);
}
