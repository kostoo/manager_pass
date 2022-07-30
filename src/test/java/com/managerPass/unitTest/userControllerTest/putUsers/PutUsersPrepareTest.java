package com.managerPass.unitTest.userControllerTest.putUsers;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

public class PutUsersPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userGenerate("kosto", "test@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate() {
        return userGenerate("userName", "email@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate(String username, String email) {
        return userGenerate(username, email, ERole.ROLE_ADMIN, true);
    }
}
