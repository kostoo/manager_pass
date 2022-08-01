package com.managerPass.unitTest.test.user_test.deleteUsersId;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class DeletePrepareTest extends PrepareServiceTest {

    protected ResultActions deleteByIdUsers(Long param) throws Exception {
        return deleteById("/api/users/{idUser}", param);
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita",
                "lastname", true);
    }

    protected UserEntity userGenerate() {
        return userServiceProvider.userGenerate("test", "test@test.ru", ERole.ROLE_ADMIN, "nik",
                "nest", true);
    }
}
