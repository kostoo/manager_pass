package com.managerPass.test.auth.authorization;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class AuthPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
            "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    public ResultActions sendPostLoginAndGetResultActions(LoginRequest loginRequest) {
        return sendPostAndGetResultActions("/api/auth", loginRequest);
    }

}
