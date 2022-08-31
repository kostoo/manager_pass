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
    @Description("Регистрация и успешная активация токена регистрации пользователя ")
    public void givenSignUpRequest_whenRegistrationActivateTokenReg_thenRegistrationActivateUser_ok() throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);
        ResultActions resultActions = sendPatchTokenAndGetResultActions(registrationResponse.getRegistrationToken());

        //then
        assertRegistrationResponse(registrationResponse);
        assert userRepositoryTest.existsByUsername(userName);

        resultActions.andExpect(status().is2xxSuccessful());

        assert userRepositoryTest.existsByUsernameAndIsAccountActiveEquals(userName, true);
    }

    @Test
    @Description("Регистрация с неудачной активацией пользователя из-за неправильного токена регистрации пользователя")
    public void givenRegistrationUser_whenRegistrationAndActivateTokenReg_thenRegistrationUnValidTokenActivate_fail()
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

        assert userRepositoryTest.existsByUsername(userName);

    }

    @Test
    @Description("Регистрация с неудачной активацией истекшего срока жизни токена регистрации пользователя")
    public void givenRegistrationUser_whenRegistrationAndActivateToken_thenTokenActivateExpiredToken_fail()
                                                                                         throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(20));
        Date dateBefore = Date.from(before);

        UserEntity user = userRepositoryTest.getUserEntityByUsername(userName);

        ValidateTokenEntity validateToken = validateTokenProvider.getValidateTokenEntityByUserEntityIdUser(
                user.getIdUser()
        );

        validateToken.setExpiryDate(dateBefore);
        validateTokenRepositoryTest.save(validateToken);

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        ResultActions resultActions = sendPatchTokenAndGetResultActions(registrationResponse.getRegistrationToken());

        //then
        assertRegistrationResponse(registrationResponse);

        assert userRepositoryTest.existsByUsername(userName);

        resultActions.andExpect(status().is4xxClientError());

    }
}
