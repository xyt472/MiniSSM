package com.lyt.AtianSpring.factory.test.test2;

import com.lyt.AtianSpring.Annotation.DAO;
import com.lyt.BabyBatisFramework.Annotation.Param;
import com.lyt.BabyBatisFramework.Annotation.Select;
import com.lyt.BabyBatisFramework.Annotation.Update;

//@DAO("daoTEST")
@DAO   //dao类必须有注解
public interface DaoTEST {
    @Update("test.updateState")
    int test(@Param("id") String id, @Param("state") int state);
}
