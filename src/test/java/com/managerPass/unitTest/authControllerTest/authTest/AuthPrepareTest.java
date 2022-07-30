package com.managerPass.unitTest.authControllerTest.authTest;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class AuthPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userGenerate("kosto", "test@test.ru", ERole.ROLE_ADMIN, true);
    }

    public ResultActions sendPostLoginAndGetResultActions(LoginRequest loginRequest) throws Exception {
        return sendPostAndGetResultActions("/api/auth", loginRequest);
    }

}
