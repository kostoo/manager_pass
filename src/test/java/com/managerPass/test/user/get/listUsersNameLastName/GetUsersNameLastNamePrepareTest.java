package com.managerPass.test.user.get.listUsersNameLastName;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;

public class GetUsersNameLastNamePrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerate("kosto", "password", ERole.ROLE_ADMIN, "name",
                "lastName", true);
    }

    protected UserEntity userGenerate(String userName, String email) {
        return userProvider.userGenerate(
                userName, email, ERole.ROLE_ADMIN, "name", "lastName", true
        );
    }

    protected UserEntity userGenerate(String userName, String email, String name, String lastName) {
        return userProvider.userGenerate(userName, email, ERole.ROLE_ADMIN, name, lastName, true);
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerate(
             "userName", "email@test.ru", ERole.ROLE_ADMIN, "name", "lastName", true
        );
    }
}
