package com.managerPass.unitTest.test.task_test.postTasks;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование добавления задачи")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class PostTasksAdminTest extends PostTasksPrepareTest {

    @Test
    @Description("Добавление задачи")
    public void addTasksWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminDbFalseGenerate("test task");

        sendPostTasksAndGetResultActions(taskEntity).andExpect(status().is2xxSuccessful())
                                                    .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }

    @Test
    @Description("Добавление задачи c пустым названием")
    public void addTasksNameNotBlankWithAdmin_fail() throws Exception {
        TaskEntity taskEntity = taskAdminDbFalseGenerate("");

        sendPostTasksAndGetResultActions(taskEntity).andExpect(status().is2xxSuccessful())
                                                    .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }
}
