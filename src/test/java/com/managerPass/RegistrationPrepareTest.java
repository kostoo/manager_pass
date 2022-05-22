package com.managerPass;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.MessageResponse;
import com.managerPass.payload.response.RegistrationResponse;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class RegistrationPrepareTest extends PrepareServiceTest {


    protected MockHttpServletResponse sendRequestAndGetMockHttpServletResponse(String urlTemplate, Object addObject)
                                                                               throws Exception {
       return mvc.perform(post(urlTemplate)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsBytes(addObject)))
                 .andReturn().getResponse();
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

    protected MockHttpServletResponse sendPatchAndGetMockHttpServletResponse(String urlTemplate,
                                                                             String registrationToken)
                                                                             throws Exception {
       return mvc.perform(patch(urlTemplate, registrationToken)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andReturn().getResponse();
    }

    protected MessageResponse sendPatchAndGetMessageResponse(String urlTemplate, String registrationToken) throws Exception {
        return new ObjectMapper().readValue(
                sendPatchAndGetMockHttpServletResponse(urlTemplate, registrationToken).getContentAsString(),
                MessageResponse.class
        );
    }

    protected SignupRequest signupRequestGenerate() {
        roleRepository.save(ObjectGeneratorUtil.generateRoleDB(ERole.ROLE_USER));
        ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
        return ObjectGeneratorUtil.signupRequestGenerate(Set.of(ERole.ROLE_USER));
    }
}
