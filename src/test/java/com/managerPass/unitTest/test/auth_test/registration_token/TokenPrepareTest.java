package com.managerPass.unitTest.test.auth_test.registration_token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import com.managerPass.unitTest.util.ObjectGeneratorUtil;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

public class TokenPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate(
            "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    protected SignupRequest signupRequestGenerate() {
        roleProvider.save(ObjectGeneratorUtil.roleGenerate(ERole.ROLE_USER));

        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }

    protected RegistrationResponse sendSignUpRequestAndGetRegistrationResponse(SignupRequest signupRequest)
                                                                              throws Exception {
        return new ObjectMapper().readValue(
                sendRequestAndGetMockHttpServletResponse("/api/register", signupRequest).getContentAsString(),
                RegistrationResponse.class
        );
    }

    protected void assertRegistrationResponse(RegistrationResponse registrationResponse) {
        assert(registrationResponse.getRegistrationToken() != null);
    }

    protected ResultActions sendPatchTokenAndGetResultActions(Object... uriVars) throws Exception {
       return sendPatchAndGetResultActions("/api/register/activate/{token}", uriVars);
    }
}
