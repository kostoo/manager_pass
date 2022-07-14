package com.managerPass.unitTest.userControllerTest.getUsersNameLastName;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

public class GetUsersNameLastNamePrepareTest extends PrepareServiceTest {

    @Override
    public void beforeClass() {
        userGenerate("kosto","password", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate(String userName, String email) {
        return userGenerate(userName, email, ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate() {
        return userGenerate("userName", "email@test.ru", ERole.ROLE_ADMIN, true);
    }
}
