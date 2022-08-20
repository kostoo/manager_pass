package com.managerPass.test.auth.registration_token;

import com.managerPass.entity.UserEntity;
import com.managerPass.entity.ValidateTokenEntity;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование работы с токенами регистрации")
public class TokenTest extends TokenPrepareTest {

    @Test
    @Description("Регистрация с активацией токена пользователя успешная")
    public void givenSignUpRequest_whenRegistrationActivateTokenReg_thenRegistrationActivateUser_ok() throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);
        ResultActions resultActions = sendPatchTokenAndGetResultActions(registrationResponse.getRegistrationToken());

        //then
        assertRegistrationResponse(registrationResponse);
        assert userProvider.existsByUsername(userName);

        resultActions.andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsernameAndIsAccountActiveEquals(userName, true);
    }

    @Test
    @Description("Неудачная попытка регистрации с активацией неверного токена пользователя")
    public void givenRegistrationUser_whenRegistrationAndActivateTokenReg_thenRegistrationBadTokenActivate_fail()
                                                                                               throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        String badToken = registrationResponse.getRegistrationToken() + 1;

        sendPatchTokenAndGetResultActions(badToken).andExpect(status().is4xxClientError());

        //then
        assertRegistrationResponse(registrationResponse);

        assert userProvider.existsByUsername(userName);

    }

    @Test
    @Description("Неудачная попытка регистрации с активацией истекшего срока токена пользователя" )
    public void givenRegistrationUser_whenRegistrationAndActivateToken_thenTokenActivateExpiredToken_fail()
                                                                                         throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(20));
        Date dateBefore = Date.from(before);

        UserEntity user = userProvider.getUserEntityByUsername(userName);

        ValidateTokenEntity validateToken = validateTokenProvider.getValidateTokenEntityByUserEntity_IdUser(
                user.getIdUser()
        );
        validateToken.setExpiryDate(dateBefore);
        validateTokenProvider.save(validateToken);

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        ResultActions resultActions = sendPatchTokenAndGetResultActions(registrationResponse.getRegistrationToken());

        //then
        assertRegistrationResponse(registrationResponse);

        assert userProvider.existsByUsername(userName);

        resultActions.andExpect(status().is4xxClientError());

    }
}
