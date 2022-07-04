package com.managerPass.unitTest.prepateTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class PrepareServiceTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected  PriorityEntityRepository priorityEntityRepository;

    @Autowired
    protected  RoleRepository roleRepository;

    @Autowired
    protected TaskEntityRepository taskEntityRepository;

    @Autowired
    protected UserEntityRepository userEntityRepository;

    @Autowired
    protected ValidateTokenRepository validateTokenRepository;


    protected MockHttpServletResponse sendRequestAndGetMockHttpServletResponse(String urlTemplate, Object addObject)
                                                                              throws Exception {
        return mvc.perform(post(urlTemplate)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsBytes(addObject)))
                  .andReturn().getResponse();
    }

    protected ResultActions sendPostAndGetResultActions(String urlTemplate, Object addObject) throws Exception {
       return mvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(addObject))
        );
    }

    protected ResultActions sendPutAndGetResultActions(String urlTemplate, Object addObject) throws Exception {
        return mvc.perform(put(urlTemplate)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsBytes(addObject))
        );
    }

    protected ResultActions getActionResult(String urlTemplate) throws Exception {
        return mvc.perform(get(urlTemplate)
                  .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions getActionResult(String urlTemplate, Object... uriVars) throws Exception {
        return mvc.perform(get(urlTemplate, uriVars)
                  .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions deleteById(String urlTemplate, Long param) throws Exception {
        return mvc.perform(delete(urlTemplate, param));
    }

    protected ResultActions sendPatchAndGetResultActions(String urlTemplate, Object... uriVars) throws Exception {
        return mvc.perform(patch(urlTemplate, uriVars)
                  .contentType(MediaType.APPLICATION_JSON));
    }

    @AfterEach
    public void deleteAllAfterTest() {
        validateTokenRepository.deleteAllInBatch();
        taskEntityRepository.deleteAllInBatch();
        userEntityRepository.deleteAllInBatch();
        priorityEntityRepository.deleteAllInBatch();
        roleRepository.deleteAllInBatch();
    }
}
