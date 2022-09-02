package com.managerPass.test.auth.registration;


import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;

@Description("Тестирование регистрации пользователя")
public class RegistrationTest extends RegistrationPrepareTest {

    @Test
    @Description("Успешная регистрация пользователя")
    public void givenRegistrationUser_whenRegistration_thenRegistration_ok()  {
        //given
        SignupRequest registrationUser = signupRequestGenerate();
        String userName = registrationUser.getUsername();

        //when
        RegistrationResponse registrationResponse = sendSignUpRequestAndGetRegistrationResponse(registrationUser);

        //then
        assert !registrationResponse.getRegistrationToken().isEmpty();

        assert userRepositoryTest.existsByUsername(userName);
    }

    @Test
    @Description("Неудачная попытка регистрации пользователя, пользователь с данным email существует")
    public void givenRegistrationUserWithExistsEmail_whenRegistration_thenRegistrationAlreadyEmailInUse_fail() {
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
    @Description("Неудачная попытка регистрации пользователя, пользователь с данным username уже существует")
    public void givenRegistrationUserWithExistsUsername_whenRegistration_thenRegistrationAlreadyUserName_fail() {
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
