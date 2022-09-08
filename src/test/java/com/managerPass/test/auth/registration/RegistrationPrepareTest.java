package com.managerPass.test.auth.registration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.prepateTest.PrepareServiceTest;
import com.managerPass.util.ObjectGeneratorUtil;
import lombok.SneakyThrows;

import java.util.Set;

public class RegistrationPrepareTest extends PrepareServiceTest {

    protected SignupRequest signupRequestGenerate() {
        roleProvider.roleGenerate(ERole.ROLE_USER);

        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }

    @SneakyThrows
    protected void sendSignUpRequest(SignupRequest signupRequest) {
            new ObjectMapper().readValue(
                    sendRequestAndGetMockHttpServletResponse(signupRequest).getContentAsString(),
                    RegistrationResponse.class
            );
    }

    @SneakyThrows
    protected MessageResponse sendSignUpRequestAndGetErrorMessage(SignupRequest signupRequest) {
            return new ObjectMapper().readValue(
                    sendRequestAndGetMockHttpServletResponse(signupRequest).getContentAsString(),
                    MessageResponse.class
            );

    }
}
