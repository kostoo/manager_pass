package com.managerPass.unitTest.authControllerTest.tokenTest;

import com.managerPass.entity.UserEntity;
import com.managerPass.entity.ValidateTokenEntity;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Description("Тестирование работы с токенами регистрации")
public class TokenTest extends TokenPrepareTest {

    @Test
    @Description("Регистрация с активацией токена пользователя успешная")
    public void registrationAndActivate_WithUnAuthorized_success() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(
                registrationUser
        );

        assertRegistrationResponse(registrationResponse);
        assert userProvider.existsByUsername(userName);

        sendPatchTokenAndGetResultActions(registrationResponse.getRegistrationToken()
        ).andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsernameAndIsAccountActiveEquals(userName, true);
    }

    @Test
    @Description("Регистрация с активацией токена пользователя fail")
    public void registrationAndActivateWithBadToken_WithUnAuthorized_fail() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        assertRegistrationResponse(registrationResponse);

        assert userProvider.existsByUsername(userName);

        String badToken = registrationResponse.getRegistrationToken() + 1;

        sendPatchTokenAndGetResultActions(badToken).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Регистрация с активацией истекшего срока токена пользователя  fail")
    public void registrationAndActivateExpiredToken_fail() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        assertRegistrationResponse(registrationResponse);

        assert userProvider.existsByUsername(userName);

        UserEntity user = userProvider.getUserEntityByUsername(userName);
        ValidateTokenEntity validateToken = validateTokenProvider.getValidateTokenEntityByUserEntity_IdUser(
                user.getIdUser()
        );

        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(20));
        Date dateBefore = Date.from(before);

        validateToken.setExpiryDate(dateBefore);
        validateTokenProvider.save(validateToken);

        sendPatchTokenAndGetResultActions(registrationResponse.getRegistrationToken()
        ).andExpect(status().is4xxClientError());
    }
}
