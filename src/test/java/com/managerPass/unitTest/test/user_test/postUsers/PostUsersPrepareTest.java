package com.managerPass.unitTest.test.user_test.postUsers;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class PostUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate(
                "kosto", "test@test.ru", ERole.ROLE_ADMIN, "name", "lastName",true
        );
    }

    protected UserEntity userGenerate(String name, String lastname, String username, String email, Boolean addInDb) {
        return userServiceProvider.userGenerate(
            username, email, ERole.ROLE_ADMIN, name, lastname, addInDb
        );
    }

    protected ResultActions sendPostUsersAndGetResultActions(Object addObject) throws Exception {
        return sendPostAndGetResultActions("/api/users", addObject);
    }
}
