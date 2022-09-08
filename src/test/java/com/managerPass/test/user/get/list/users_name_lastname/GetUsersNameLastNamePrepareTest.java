package com.managerPass.test.user.get.list.users_name_lastname;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;

public class GetUsersNameLastNamePrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "password", ERole.ROLE_ADMIN, "name", "lastName");
    }

    protected UserEntity userGenerate(String userName, String email) {
        return userProvider.userGenerateDb(userName, email, ERole.ROLE_ADMIN, "name", "lastName");
    }

    protected UserEntity userGenerate(String userName, String email, String name, String lastName) {
        return userProvider.userGenerateDb(userName, email, ERole.ROLE_ADMIN, name, lastName);
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerateDb("userName", "email@test.ru", ERole.ROLE_ADMIN, "name", "lastName");
    }
}
