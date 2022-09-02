package com.managerPass.test.auth.registration;


import com.managerPass.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;

@Description("Регистрация пользователя")
public class RegistrationTest extends RegistrationPrepareTest {

    @Test
    @Description("Успешная регистрация пользователя")
    public void givenRegistrationUser_whenRegistration_thenRegistration_ok()  {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        sendSignUpRequest(registrationUser);

        //then
        assert userRepositoryTest.existsByUsername(userName);
    }

    @Test
    @Description("Неудачная попытка регистрации пользователя, пользователь с данным email уже существует")
    public void givenRegistrationUserWithExistsEmail_whenRegistration_thenRegistrationAlreadyEmailInUse_fail() {
        //given
        SignupRequest registrationUser = signupRequestGenerate();

        //when
        sendSignUpRequest(registrationUser);

        registrationUser.setUsername("notAlreadyUse");

        //then

        assert sendSignUpRequestAndGetErrorMessage(registrationUser).getMessage().equals(
                 String.format("Email %s is already in use!", registrationUser.getEmail())
        );
    }

    @Test
    @Description("Неудачная попытка регистрации пользователя, пользователь с данным username уже существует")
    public void givenRegistrationUserWithExistsUsername_whenRegistration_thenRegistrationAlreadyUserName_fail() {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        sendSignUpRequest(registrationUser);

        //then
        assert sendSignUpRequestAndGetErrorMessage(registrationUser).getMessage().equals(
                String.format("Username %s is already taken!", userName)
        );
    }

}
