package com.managerPass.test.auth.registration;


import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;

@Description("Тестирование регистрации пользователя")
public class RegistrationTest extends RegistrationPrepareTest {

    @Test
    @Description("Регистрация пользователя успешная")
    public void givenRegistrationUser_whenRegistration_thenRegistration_ok() throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        //then
        assert !registrationResponse.getRegistrationToken().isEmpty();

        assert userProvider.existsByUsername(userName);
    }

    @Test
    @Description("Повторная регистрация пользователя с одинаковым email неудачная")
    public void givenRegistrationUser_whenRegistration_thenRegistrationAlreadyEmailInUse_fail() throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        registrationUser.setUsername("notAlreadyUse");

        //then
        assert !registrationResponse.getRegistrationToken().isEmpty();

        assert sendSignUpRequestAndGetErrorMessage(registrationUser).getMessage().equals(
                 String.format("Email %s is already in use!", registrationUser.getEmail())
        );
    }

    @Test
    @Description("Попытка регистрации пользователя с cуществующим username")
    public void givenRegistrationUser_whenRegistration_thenRegistrationAlreadyUserName_fail() throws Exception {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        //then
        assert !registrationResponse.getRegistrationToken().isEmpty();

        assert sendSignUpRequestAndGetErrorMessage(registrationUser).getMessage().equals(
                String.format("Username %s is already taken!", userName)
        );
    }

}
