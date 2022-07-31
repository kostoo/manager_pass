package com.managerPass.unitTest.test.user_test.postUsers;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class PostUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate("test", "test@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerateDbFalse() {
        return userServiceProvider.userGenerate("userName", "email@test.ru", ERole.ROLE_ADMIN, false);
    }

    protected ResultActions sendPostUsersAndGetResultActions(Object addObject) throws Exception {
        return sendPostAndGetResultActions("/api/users", addObject);
    }
}
