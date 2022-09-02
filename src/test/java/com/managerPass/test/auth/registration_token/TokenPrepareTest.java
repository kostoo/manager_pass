package com.managerPass.test.auth.registration_token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.prepateTest.PrepareServiceTest;
import com.managerPass.util.ObjectGeneratorUtil;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public class TokenPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
            "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    protected SignupRequest signupRequestGenerate() {
        roleProvider.roleGenerate(ERole.ROLE_USER);

        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }

    protected RegistrationResponse sendSignUpRequestAndGetRegistrationResponse(SignupRequest signupRequest) {
        try {
            return new ObjectMapper().readValue(
               sendRequestAndGetMockHttpServletResponse(signupRequest).getContentAsString(), RegistrationResponse.class
            );
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void assertRegistrationResponse(RegistrationResponse registrationResponse) {
        assert(registrationResponse.getRegistrationToken() != null);
    }

    protected ResultActions sendPatchTokenAndGetResultActions(Object... uriVars) {
       return sendPatchAndGetResultActions(uriVars);
    }
}
