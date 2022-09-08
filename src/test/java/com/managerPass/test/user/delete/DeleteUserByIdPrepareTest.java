package com.managerPass.test.user.delete;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class DeleteUserByIdPrepareTest extends PrepareServiceTest {

    protected ResultActions deleteByIdUsers(Long param) {
        return deleteById("/api/users/{idUser}", param);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname");
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerateDb("test", "test@test.ru", ERole.ROLE_ADMIN, "nik", "nest");
    }
}
