package com.managerPass.test.auth.authorization;

import com.managerPass.payload.request.LoginRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("тестирование авторизации пользователя")
public class AuthTest extends AuthPrepareTest {

    @Test
    @Description("Успешная авторизация пользователя")
    public void auth_Admin_success() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("kosto", "password");

        //when
        ResultActions resultActions = sendPostLoginAndGetResultActions(loginRequest);

        //then
        resultActions.andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsername(loginRequest.getUsername());
    }

    @Test
    @Description("Недуачная авторизация пользователя при ошибочном логине")
    public void auth_InvalidUserName_fail() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("kosto", "password");

        //when
        ResultActions resultActions = sendPostLoginAndGetResultActions(loginRequest);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Недуачная попытка авторизации пользователя при ошибочном пароле")
    public void auth_InvalidPassword_fail() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("invalidUser", "password");

        //when
        ResultActions resultActions = sendPostLoginAndGetResultActions(loginRequest);

        //then
        resultActions.andExpect(status().is4xxClientError());

    }
}
