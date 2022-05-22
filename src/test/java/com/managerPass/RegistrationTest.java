package com.managerPass;

import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.payload.response.RegistrationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
@Description("Тестирование регистрации пользователя")
public class RegistrationTest extends  RegistrationPrepareTest{


    @Test
    @Description("Повторная регистрация пользователя с одинаковым username неудачная")
    public void registrationAlreadyUserName_WithUnAuthorized_fail() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(
                registrationUser , "/api/register"
        );

        registrationUser.setUsername("notAlreadyUse");
        assertRegistrationResponse(registrationResponse);

        MessageResponse registrationResponseError = sendSignUpRequestAndGetErrorMessage(
                registrationUser , "/api/register"
        );

        assert registrationResponseError.getMessage().equals(
                String.format("Email %s is already in use!", registrationUser.getEmail())
        );
        assert checkExistsUserEntityByUserName(userName);
    }

    @Test
    @Description("Повторная регистрация пользователя с одинаковым username неудачная")
    public void registrationAlreadyEmail_WithUnAuthorized_fail() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        MessageResponse registrationResponseError = sendSignUpRequestAndGetErrorMessage(
                registrationUser , "/api/register"
        );

        assert registrationResponseError.getMessage().equals(String.format("Username %s is already taken!", userName));
        assert checkExistsUserEntityByUserName(userName);
    }

    @Test
    @Description("Регистрация пользователя успешная")
    public void registration_WithUnAuthorized_success() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(
                registrationUser , "/api/register"
        );

        assertRegistrationResponse(registrationResponse);
        assert checkExistsUserEntityByUserName(userName);

    }

    @Test
    @Description("Регистрация с активацией токена пользователя успешная")
    public void registrationAndActivate_WithUnAuthorized_success() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(
                registrationUser , "/api/register"
        );

        assertRegistrationResponse(registrationResponse);
        assert checkExistsUserEntityByUserName(userName);

        sendPatchAndGetMockHttpServletResponse(
                "/api/register/activate/{token}", registrationResponse.getRegistrationToken()
        );

        checkUserEntityIsActive(userName);
    }

    @Test
    @Description("Регистрация с активацией токена пользователя fail")
    public void registrationAndActivateWithBadToken_WithUnAuthorized_fail() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(
                registrationUser , "/api/register"
        );

        assertRegistrationResponse(registrationResponse);
        assert checkExistsUserEntityByUserName(userName);

        String badToken = registrationResponse.getRegistrationToken() + 1;

        MessageResponse registrationResponseToken = sendPatchAndGetMessageResponse(
                "/api/register/activate/{token}", badToken
        );

        assert registrationResponseToken.getMessage().equals(String.format("Token  %s not found ", badToken));
        assert checkUserEntityIsActive(userName);
    }


}
