package com.managerPass;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.RegistrationResponse;
import com.managerPass.repository.UserEntityRepository;
import com.managerPass.repository.ValidateTokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    ValidateTokenRepository validateTokenRepository;

    SignupRequest signupRequest;
    RegistrationResponse registrationResponse;

    @BeforeEach
    public void prepareDataBeforeRegistration() {
        signupRequest = new SignupRequest();
        signupRequest.setUsername(RandomStringUtils.random(10, true, false));
        signupRequest.setRole(Set.of(ERole.ROLE_USER, ERole.ROLE_ADMIN));
        signupRequest.setEmail("neesterov1@gmail.com");
        signupRequest.setPassword("password");
    }

    @Test
    @WithMockUser( username = "kosto", roles = "ADMIN")
    public void registrationWithAdmin_success() throws Exception {
       String registrationResponseString = mvc.perform(post("/api/register")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsBytes(signupRequest)))
                                              .andDo(print()).andExpect(status().is2xxSuccessful())
                                              .andReturn().getResponse().getContentAsString();

       registrationResponse = new ObjectMapper().readValue(registrationResponseString, RegistrationResponse.class);
       assert registrationResponse != null;

       mvc.perform(patch("/api/register/activate/{token}", registrationResponse.getRegistrationToken())
          .contentType(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser( username = "kosto", roles = "USER")
    public void registrationWithUser_success() throws Exception {
        String registrationResponseString = mvc.perform(post("/api/register")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(objectMapper.writeValueAsBytes(signupRequest)))
                                               .andDo(print()).andExpect(status().is2xxSuccessful())
                                               .andReturn().getResponse().getContentAsString();

        registrationResponse = new ObjectMapper().readValue(registrationResponseString, RegistrationResponse.class);
        assert registrationResponse != null;

        mvc.perform(patch("/api/register/activate/{token}", registrationResponse.getRegistrationToken())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void registrationWithUnAuthorized_success() throws Exception {
        String registrationResponseString = mvc.perform(post("/api/register")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(objectMapper.writeValueAsBytes(signupRequest)))
                                               .andDo(print()).andExpect(status().is2xxSuccessful())
                                               .andReturn().getResponse().getContentAsString();

        registrationResponse = new ObjectMapper().readValue(registrationResponseString, RegistrationResponse.class);
        assert registrationResponse != null;

        mvc.perform(patch("/api/register/activate/{token}", registrationResponse.getRegistrationToken())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().is2xxSuccessful());
    }

    @AfterEach
    public void deleteAllAfterRegistration() {
        validateTokenRepository.deleteAllInBatch();
        userEntityRepository.deleteAllInBatch();
    }
}
