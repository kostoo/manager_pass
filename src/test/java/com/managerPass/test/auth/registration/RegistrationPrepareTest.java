package com.managerPass.test.auth.registration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.prepateTest.PrepareServiceTest;
import com.managerPass.util.ObjectGeneratorUtil;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public class RegistrationPrepareTest extends PrepareServiceTest {

    protected SignupRequest signupRequestGenerate() {
        roleProvider.roleGenerate(ERole.ROLE_USER);

        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }

    protected void sendSignUpRequest(SignupRequest signupRequest) {

        try {
            new ObjectMapper().readValue(
                    sendRequestAndGetMockHttpServletResponse(signupRequest).getContentAsString(),
                    RegistrationResponse.class
            );
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected MessageResponse sendSignUpRequestAndGetErrorMessage(SignupRequest signupRequest) {
        try {
            return new ObjectMapper().readValue(
                    sendRequestAndGetMockHttpServletResponse(signupRequest).getContentAsString(),
                    MessageResponse.class
            );
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
