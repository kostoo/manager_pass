package com.managerPass.unitTest.registrationProviderTest.prepareTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.unitTest.registrationProviderTest.ObjectGeneratorUtil;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

import java.util.Set;

public class RegistrationPrepareTest extends PrepareServiceTest {

    protected SignupRequest signupRequestGenerate() {
        roleRepository.save(ObjectGeneratorUtil.generateRoleDB(ERole.ROLE_USER));
        ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }

    protected RegistrationResponse sendSignUpRequestAndGetRegistrationResponse(SignupRequest signupRequest,
                                                                               String urlTemplate) throws Exception {
        return new ObjectMapper().readValue(
                sendRequestAndGetMockHttpServletResponse(urlTemplate, signupRequest).getContentAsString(),
                RegistrationResponse.class
        );
    }

    protected MessageResponse sendSignUpRequestAndGetErrorMessage(SignupRequest signupRequest, String urlTemplate)
                                                                  throws Exception {
        return new ObjectMapper().readValue(
                sendRequestAndGetMockHttpServletResponse(urlTemplate, signupRequest).getContentAsString(),
                MessageResponse.class
        );
    }

    protected void assertRegistrationResponse(RegistrationResponse registrationResponse) {
       assert(registrationResponse.getRegistrationToken() != null);
    }

    protected Boolean checkExistsUserEntityByUserName(String userName) {
        return userEntityRepository.existsByUsername(userName);
    }

    protected Boolean checkUserEntityIsActive(String username) {
        return userEntityRepository.findByUsername(username).get().getIsAccountActive();
    }


}
