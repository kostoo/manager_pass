package com.managerPass.unitTest.taskControllerTest.postTasks;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "kosto", roles = "ADMIN")
public class PostTasksTest extends PostTasksPrepareTest{

    @Test
    @Description("Добавление задачи")
    public void addTasksWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminDbFalseGenerate();

        sendPostTasksAndGetResultActions(taskEntity)
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }
}
