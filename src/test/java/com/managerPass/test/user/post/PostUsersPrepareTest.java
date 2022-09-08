package com.managerPass.test.user.post;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class PostUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "name", "lastName");
    }

    protected UserEntity userGenerate(String name, String lastname, String username, String email) {
        return userProvider.userGenerate(username, email, ERole.ROLE_ADMIN, name, lastname);
    }

    protected UserEntity userGenerateDb(String name, String lastname, String username, String email) {
        return userProvider.userGenerateDb(username, email, ERole.ROLE_ADMIN, name, lastname);
    }

    protected ResultActions sendPostUsersAndGetResultActions(Object addObject) {
        return sendPostAndGetResultActions("/api/users", addObject);
    }
}
