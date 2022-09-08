package com.managerPass.test.user.get.user_username;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetUsersUsernamePrepareTest extends PrepareServiceTest {

    protected ResultActions getActionResultUserName(String username) {
        return getActionResult("/api/users/username?username={username}", username);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("test", "test@test.ru", ERole.ROLE_ADMIN, "name", "lastName");
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerateDb("userName", "email@test.ru", ERole.ROLE_ADMIN, "name", "lastName");
    }
}
