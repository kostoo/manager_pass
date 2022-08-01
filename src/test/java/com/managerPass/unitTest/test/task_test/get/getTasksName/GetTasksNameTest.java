package com.managerPass.unitTest.test.task_test.get.getTasksName;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование получения задачи по названию задачи")
public class GetTasksNameTest extends GetTasksNamePrepareTest{

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение задач по названию администратором")
    public void getTasksName_Admin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult(
                "/api/tasks?name={name}", taskEntity.getName()
        ).andExpect(status().is2xxSuccessful())
         .andExpect(jsonPath("$[0].idTask").value(taskEntity.getIdTask()))
         .andExpect(jsonPath("$[0].name").value(taskEntity.getName()));
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Получение задач по названию")
    public void getTasksNameNull_Admin_fail() throws Exception {
        getActionsTasksName(null).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Получение задач по названию")
    public void getTasksName_UnAuthorized_fail() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult("/api/tasks?name={name}", taskEntity.getName()).andExpect(status().isUnauthorized());
    }
}
