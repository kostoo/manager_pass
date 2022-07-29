package com.managerPass.unitTest.userControllerTest.getUsersUserName;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetUsersUsernamePrepareTest extends PrepareServiceTest {

    protected ResultActions getActionResultUserName(String param) throws Exception {
        return getActionResult("/api/users/userName/{userName}", param);
    }

    @Override
    public void beforeClass() {
        userGenerate("test", "test@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate() {
        return userGenerate("userName", "email@test.ru", ERole.ROLE_ADMIN, true);
    }
}
