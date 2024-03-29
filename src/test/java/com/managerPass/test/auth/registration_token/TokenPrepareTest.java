package com.managerPass.test.auth.registration_token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.prepateTest.PrepareServiceTest;
import com.managerPass.util.ObjectGeneratorUtil;
import lombok.SneakyThrows;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

public class TokenPrepareTest extends PrepareServiceTest {

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname");
    }

    protected SignupRequest signupRequestGenerate() {
        roleProvider.roleGenerate(ERole.ROLE_USER);

        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }

    @SneakyThrows
    protected RegistrationResponse sendSignUpRequestAndGetRegistrationResponse(SignupRequest signupRequest) {
        return new ObjectMapper().readValue(
               sendRequestAndGetMockHttpServletResponse(signupRequest).getContentAsString(), RegistrationResponse.class
        );
    }

    protected void assertRegistrationResponse(RegistrationResponse registrationResponse) {
        assert(registrationResponse.getRegistrationToken() != null);
    }

    protected ResultActions sendPatchTokenAndGetResultActions(Object... uriVars) {
       return sendPatchAndGetResultActions(uriVars);
    }
}
