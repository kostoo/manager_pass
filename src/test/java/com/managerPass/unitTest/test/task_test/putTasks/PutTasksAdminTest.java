package com.managerPass.unitTest.test.task_test.putTasks;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.response.TaskResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование обновления задач")
public class PutTasksAdminTest extends PutTasksPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное обновление задачи c ролью администратора")
    public void updateTasks_Admin_ok() throws Exception {
        TaskEntity taskEntity = taskAddGenerate();
        TaskResponse updateTask = taskUpdateGenerate(
                "updateTaskAdmin", "update message", EPriority.LOW, ERole.ROLE_ADMIN
        );

        sendPutTaskResponseAndGetResultActions(
                updateTask, taskEntity.getIdTask()
        ).andExpect(status().is2xxSuccessful())
         .andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
         .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Обновление задачи c ролью пользователя")
    public void updateTasks_user_ok() throws Exception {
        TaskEntity taskEntity = taskAddGenerate();
        TaskResponse updateTask = taskUpdateGenerate(
                "updateTaskUser", "update message", EPriority.LOW, ERole.ROLE_USER
        );

        sendPutTaskResponseAndGetResultActions(
                updateTask, taskEntity.getIdTask()
        ).andExpect(status().is2xxSuccessful())
         .andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
         .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }

    @Test
    @Description("Обновление задачи без авторизации")
    public void updateTasks_unAuthorized_fail() throws Exception {
        TaskEntity taskEntity = taskAddGenerate();
        TaskResponse updateTask = taskUpdateGenerate(
                "updateTaskUser", "update message", EPriority.LOW, ERole.ROLE_USER
        );

        sendPutTaskResponseAndGetResultActions(updateTask, taskEntity.getIdTask()).andExpect(status().isUnauthorized());

    }
}
