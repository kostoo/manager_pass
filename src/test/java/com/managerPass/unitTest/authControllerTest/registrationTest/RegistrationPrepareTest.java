package com.managerPass.unitTest.authControllerTest.registrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import com.managerPass.unitTest.util.ObjectGeneratorUtil;

import java.util.Set;

import static com.managerPass.unitTest.util.ObjectGeneratorUtil.roleGenerate;

public class RegistrationPrepareTest extends PrepareServiceTest {

    protected SignupRequest signupRequestGenerate() {
        roleProvider.save(roleGenerate(ERole.ROLE_USER));

        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }

    protected RegistrationResponse sendSignUpRequestAndGetRegistrationResponse(SignupRequest signupRequest)
                                                                              throws Exception {
        return new ObjectMapper().readValue(
                sendRequestAndGetMockHttpServletResponse("/api/register", signupRequest).getContentAsString(),
                RegistrationResponse.class
        );
    }

    protected MessageResponse sendSignUpRequestAndGetErrorMessage(SignupRequest signupRequest)
                                                                  throws Exception {
        return new ObjectMapper().readValue(
                sendRequestAndGetMockHttpServletResponse("/api/register", signupRequest).getContentAsString(),
                MessageResponse.class
        );
    }
}
