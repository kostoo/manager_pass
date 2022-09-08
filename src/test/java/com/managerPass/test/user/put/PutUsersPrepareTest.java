package com.managerPass.test.user.put;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.payload.request.user.UserRequest;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class PutUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "nest");
    }

    protected UserRequest userUpdateGenerate() {
        return userProvider.userRequestGenerate(
                         userProvider.userGenerate("update", "update@email", ERole.ROLE_USER, "nik", "nest")
        );
    }

    protected ResultActions sendPutUserRequestAndGetResultActions(UserRequest updateObject, Long idUser) {
        return sendPutAndGetResultActions(updateObject, idUser);
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerateDb("userName", "email@test.ru", ERole.ROLE_ADMIN, "nikita", "nest");
    }

    protected UserEntity userGenerate(String username, String email) {
        return userProvider.userGenerateDb(username, email, ERole.ROLE_ADMIN, "nikita", "nest");
    }
}
