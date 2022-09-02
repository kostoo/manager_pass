package com.managerPass.test.user.post;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class PostUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
                "kosto", "test@test.ru", ERole.ROLE_ADMIN, "name", "lastName",true
        );
    }

    protected UserEntity userGenerate(String name, String lastname, String username, String email, Boolean addInDb) {
        return userProvider.userGenerate(username, email, ERole.ROLE_ADMIN, name, lastname, addInDb);
    }

    protected ResultActions sendPostUsersAndGetResultActions(Object addObject) throws Exception {
        return sendPostAndGetResultActions("/api/users", addObject);
    }
}
