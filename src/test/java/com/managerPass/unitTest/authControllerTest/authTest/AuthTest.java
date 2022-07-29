package com.managerPass.unitTest.authControllerTest.authTest;

import com.managerPass.payload.request.LoginRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("тестирование авторизации пользователя")
public class AuthTest extends AuthPrepareTest {

    @Test
    @Description("Авторизация пользователя success")
    public void authAdmin_success() throws Exception {
        LoginRequest loginRequest = new LoginRequest("kosto", "password");

        sendPostLoginAndGetResultActions(loginRequest).andExpect(status().is2xxSuccessful());
    }

    @Test
    @Description("Авторизация пользователя fail")
    public void authInvalidUserName_fail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("invalidUser", "password");

        sendPostLoginAndGetResultActions(loginRequest).andExpect(status().is4xxClientError());
    }
}
