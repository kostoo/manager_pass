package com.managerPass.test.user.delete;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class DeleteUserIdUserPrepareTest extends PrepareServiceTest {

    protected ResultActions deleteByIdUsers(Long param) throws Exception {
        return deleteById("/api/users/{idUser}", param);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
             "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    protected UserEntity userGenerate() {
        return userProvider.userGenerate(
                "test", "test@test.ru", ERole.ROLE_ADMIN, "nik", "nest", true
        );
    }
}
