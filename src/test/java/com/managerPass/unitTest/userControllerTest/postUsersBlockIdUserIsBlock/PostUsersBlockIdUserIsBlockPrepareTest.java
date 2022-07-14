package com.managerPass.unitTest.userControllerTest.postUsersBlockIdUserIsBlock;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

public class PostUsersBlockIdUserIsBlockPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeClass() {
        userGenerate("test","test@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected UserEntity userGenerate() {
        return userGenerate("userName", "email@test.ru", ERole.ROLE_ADMIN, true);
    }
}
