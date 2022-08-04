package com.managerPass.test.user.putUsers;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;

public class PutUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate(
                "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita","nest",true
        );
    }

    protected UserEntity userGenerate() {
        return userServiceProvider.userGenerate(
                "userName", "email@test.ru", ERole.ROLE_ADMIN,"nikita","nest", true
        );
    }

    protected UserEntity userGenerate(String username, String email) {
        return userServiceProvider.userGenerate(
                username, email, ERole.ROLE_ADMIN, "nikita","nest",true
        );
    }
}
