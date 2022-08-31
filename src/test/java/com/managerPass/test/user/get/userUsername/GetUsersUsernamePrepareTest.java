package com.managerPass.test.user.get.userUsername;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetUsersUsernamePrepareTest extends PrepareServiceTest {

    protected ResultActions getActionResultUserName(String username) throws Exception {
        return getActionResult("/api/users/username?username={username}", username);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
                "test", "test@test.ru", ERole.ROLE_ADMIN, "name", "lastName", true
        );
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerate(
             "userName", "email@test.ru", ERole.ROLE_ADMIN, "name", "lastName", true
        );
    }
}
