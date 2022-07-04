package com.managerPass.unitTest.registrationProviderTest;


import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.unitTest.registrationProviderTest.prepareTest.RegistrationPrepareTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Description("Тестирование регистрации пользователя")
public class RegistrationProviderTest extends RegistrationPrepareTest {


    @Test
    @Description("Повторная регистрация пользователя с одинаковым email неудачная")
    public void registrationAlreadyEmail_WithUnAuthorized_fail() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(
                registrationUser , "/api/register"
        );

        registrationUser.setUsername("notAlreadyUse");
        assertRegistrationResponse(registrationResponse);

         assert sendSignUpRequestAndGetErrorMessage(registrationUser , "/api/register").getMessage().equals(
                 String.format("Email %s is already in use!", registrationUser.getEmail())
         );

        assert checkExistsUserEntityByUserName(userName);
    }

    @Test
    @Description("Повторная регистрация пользователя с одинаковым username неудачная")
    public void registrationAlreadyUserName_WithUnAuthorized_fail() throws Exception {

        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(
                registrationUser , "/api/register"
        );

        assertRegistrationResponse(registrationResponse);

        assert sendSignUpRequestAndGetErrorMessage(registrationUser , "/api/register").getMessage().equals(
                String.format("Username %s is already taken!", userName)
        );

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

        sendPatchAndGetResultActions(
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

        sendPatchAndGetResultActions("/api/register/activate/{token}", badToken
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


}
