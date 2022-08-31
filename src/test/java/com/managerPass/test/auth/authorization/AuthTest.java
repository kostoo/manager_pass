package com.managerPass.test.auth.authorization;

import com.managerPass.payload.request.LoginRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Авторизация пользователя")
public class AuthTest extends AuthPrepareTest {

    @Test
    @Description("Успешная авторизация пользователя")
    public void givenLoginRequest_whenAuth_thenLogin_ok() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("kosto", "password");

        //when
        ResultActions resultActions = sendPostLoginAndGetResultActions(loginRequest);

        //then
        resultActions.andExpect(status().is2xxSuccessful());

        assert userRepositoryTest.existsByUsername(loginRequest.getUsername());
    }

    @Test
    @Description("Неудачная авторизация пользователя c неправильным логином")
    public void givenLoginRequest_whenAuth_thenInvalidUserName_fail() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("kosto", "password");

        //when
        ResultActions resultActions = sendPostLoginAndGetResultActions(loginRequest);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Неудачная попытка авторизации пользователя с неправильным паролем")
    public void givenLoginRequest_whenAuth_thenInvalidPassword_fail() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("invalidUser", "password");

        //when
        ResultActions resultActions = sendPostLoginAndGetResultActions(loginRequest);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }
}
