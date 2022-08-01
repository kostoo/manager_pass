package com.managerPass.unitTest.test.task_test.get.getTasksId;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование получения задачи по id")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class GetTasksAdminTest extends GetTasksPrepareTest {

    @Test
    @Description("Получение задачи по id")
    public void getTasksIdTaskWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getTasksActionResult(taskEntity.getIdTask()).andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
                                                    .andExpect(jsonPath("$.name").value(taskEntity.getName()))
                                                    .andExpect(jsonPath("$.message").value(taskEntity.getMessage()))
                                                    .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Description("Получение задачи по несуществующему id")
    public void getTasksIdTaskWithAdmin_fail() throws Exception {
        taskAdminGenerate();

        getTasksActionResult(0L).andExpect(status().is4xxClientError());
    }
}
