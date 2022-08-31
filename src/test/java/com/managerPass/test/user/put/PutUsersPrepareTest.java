package com.managerPass.test.user.put;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.prepateTest.PrepareServiceTest;
import com.managerPass.util.UserConverter;
import org.springframework.test.web.servlet.ResultActions;

public class PutUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
                "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita","nest",true
        );
    }

    protected UserRequest userUpdateGenerate() {
        return UserConverter.userRequestGenerate(
                userProvider.userGenerate(
                    "update", "update@email", ERole.ROLE_USER, "nik", "nest", false
                )
        );
    }

    protected ResultActions sendPutUserRequestAndGetResultActions(UserRequest updateObject, Long idUser)
                                                                                      throws Exception {
        return sendPutAndGetResultActions(updateObject, idUser);
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerate(
                "userName", "email@test.ru", ERole.ROLE_ADMIN,"nikita","nest", true
        );
    }

    protected UserEntity userGenerate(String username, String email) {
        return userProvider.userGenerate(
                username, email, ERole.ROLE_ADMIN, "nikita","nest",true
        );
    }
}
