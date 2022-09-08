package com.managerPass.test.user.get.user_id;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetUsersPrepareTest extends PrepareServiceTest {

    protected ResultActions getActionResultIdUser(Long param) {
        return getActionResult("/api/users/{idUser}", param);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname");
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerateDb("test", "test@test.ru", ERole.ROLE_ADMIN, "nik", "nest");
    }
}
