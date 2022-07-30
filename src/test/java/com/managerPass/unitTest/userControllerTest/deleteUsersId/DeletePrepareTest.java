package com.managerPass.unitTest.userControllerTest.deleteUsersId;

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
        userGenerate("kosto", "password", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate() {
        return userGenerate("test", "test@test.ru", ERole.ROLE_ADMIN, true);
    }
}
