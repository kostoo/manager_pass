package com.managerPass.prepateTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.provider.PriorityProvider;
import com.managerPass.provider.RoleProvider;
import com.managerPass.provider.ValidateTokenProvider;
import com.managerPass.provider.repository.PriorityRepositoryTest;
import com.managerPass.provider.repository.RoleRepositoryTest;
import com.managerPass.provider.repository.TaskRepositoryTest;
import com.managerPass.provider.TaskProvider;
import com.managerPass.provider.repository.UserRepositoryTest;
import com.managerPass.provider.UserProvider;
import com.managerPass.provider.repository.ValidateTokenRepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
    protected PriorityRepositoryTest priorityRepositoryTest;

    @Autowired
    protected RoleRepositoryTest roleRepositoryTest;

    @Autowired
    protected TaskRepositoryTest taskRepositoryTest;

    @Autowired
    protected UserRepositoryTest userRepositoryTest;

    @Autowired
    protected ValidateTokenProvider validateTokenProvider;

    @Autowired
    protected RoleProvider roleProvider;

    @Autowired
    protected PriorityProvider priorityProvider;

    @Autowired
    protected ValidateTokenRepositoryTest validateTokenRepositoryTest;

    @Autowired
    protected TaskProvider taskProvider;

    @Autowired
    protected UserProvider userProvider;

    protected MockHttpServletResponse sendRequestAndGetMockHttpServletResponse(Object addObject) {
        try {
            return mvc.perform(post("/api/register")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsBytes(addObject)))
                      .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ResultActions sendPostAndGetResultActions(String urlTemplate, Object addObject) {
        try {
            return mvc.perform(post(urlTemplate)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsBytes(addObject))
             );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ResultActions sendPutAndGetResultActions(Object addObject) {
        try {
            return mvc.perform(put("/api/users")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsBytes(addObject))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ResultActions sendPutAndGetResultActions(Object addObject, Object... uriVars) {
        try {
            return mvc.perform(put("/api/tasks/{idTask}", uriVars)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsBytes(addObject))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ResultActions getActionResult(String urlTemplate) {
        try {
            return mvc.perform(get(urlTemplate)
                      .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ResultActions getActionResult(String urlTemplate, Object... uriVars) {
        try {
            return mvc.perform(get(urlTemplate, uriVars)
                      .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ResultActions deleteById(String urlTemplate, Long param) {
        try {
            return mvc.perform(delete(urlTemplate, param));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ResultActions sendPatchAndGetResultActions(Object... uriVars) {
        try {
            return mvc.perform(patch("/api/register/activate/{token}", uriVars)
                      .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeTestClass
    public void beforeTest() {
    }

    @AfterEach
    public void deleteAllAfterTest() {
        validateTokenRepositoryTest.deleteAllInBatch();
        taskRepositoryTest.deleteAllInBatch();
        userRepositoryTest.deleteAllInBatch();
        priorityRepositoryTest.deleteAllInBatch();
        roleRepositoryTest.deleteAllInBatch();
    }
}
