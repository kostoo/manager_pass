package com.managerPass.unitTest.test.user_test.getUserId;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetUsersPrepareTest extends PrepareServiceTest {

    protected ResultActions getActionResultIdUser(Long param) throws Exception {
        return getActionResult("/api/users/{idUser}", param);
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate("kosto", "password", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate() {
        return userServiceProvider.userGenerate("test", "test@test.ru", ERole.ROLE_ADMIN, true);
    }
}
