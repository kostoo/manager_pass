package com.managerPass.test.user.get.userId;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetUsersPrepareTest extends PrepareServiceTest {

    protected ResultActions getActionResultIdUser(Long param) throws Exception {
        return getActionResult("/api/users/{idUser}", param);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerate("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita",
                "lastname", true);
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerate(
                "test", "test@test.ru", ERole.ROLE_ADMIN, "nik","nest", true
        );
    }
}
