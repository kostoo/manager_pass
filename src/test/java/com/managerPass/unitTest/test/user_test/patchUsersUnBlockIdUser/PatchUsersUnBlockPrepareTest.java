package com.managerPass.unitTest.test.user_test.patchUsersUnBlockIdUser;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

public class PatchUsersUnBlockPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate(
                "test", "test@test.ru", ERole.ROLE_ADMIN, "name", "lastname", true
        );
    }

    protected UserEntity userGenerate() {
        return userServiceProvider.userGenerate(
                "userName", "email@test.ru", ERole.ROLE_ADMIN, "nik", "nest", true
        );
    }
}
