package com.managerPass.unitTest.test.user_test.getUsersUserName;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetUsersUsernamePrepareTest extends PrepareServiceTest {

    protected ResultActions getActionResultUserName(String param) throws Exception {
        return getActionResult("/api/users/userName/{userName}", param);
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate("test", "test@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate() {
        return userServiceProvider.userGenerate("userName", "email@test.ru", ERole.ROLE_ADMIN, true);
    }
}
