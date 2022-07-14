package com.managerPass.unitTest.prepateTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.repository.test.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static com.managerPass.unitTest.util.ObjectGeneratorUtil.taskEntityGeneration;
import static com.managerPass.unitTest.util.ObjectGeneratorUtil.userEntityGeneration;
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
    protected PriorityProvider priorityProvider;

    @Autowired
    protected RoleProvider roleProvider;

    @Autowired
    protected TaskProvider taskProvider;

    @Autowired
    protected UserProvider userProvider;

    @Autowired
    protected ValidateTokenProvider validateTokenProvider;


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

    protected UserEntity userGenerate(String userName, String email, ERole eRole, Boolean addInDb) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(eRole);
        roleEntity = roleProvider.save(roleEntity);

        UserEntity user = userEntityGeneration(userName, email, roleEntity);
        if (addInDb) {
           return userProvider.save(user);
        } else {
           return user;
        }
    }

    protected TaskEntity taskGenerate(String name , String message, EPriority ePriority,
                                      ERole eRole, Boolean addInDb) {

        PriorityEntity priorityEntity = new PriorityEntity();
        priorityEntity.setName(ePriority);

        priorityEntity = priorityProvider.save(priorityEntity);

        TaskEntity task = taskEntityGeneration(
                name, message, priorityEntity, userGenerate("test", "test@test.ru", eRole, true)
        );
        if (addInDb) {
          return taskProvider.save(task);
        } else {
           return task;
        }
    }

    @BeforeTestClass
    public void beforeClass() {
    }

    @BeforeEach
    public void beforeMethod() {
    }

    @AfterEach
    public void deleteAllAfterTest() {
        validateTokenProvider.deleteAllInBatch();
        taskProvider.deleteAllInBatch();
        userProvider.deleteAllInBatch();
        priorityProvider.deleteAllInBatch();
        roleProvider.deleteAllInBatch();
    }
}
