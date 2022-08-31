package com.managerPass.test.task.put;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.request.TaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование обновления задач")
public class PutTasksTest extends PutTasksPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное обновление задачи c ролью администратора")
    public void givenTask_whenUpdateTasksById_thenUpdateTask_roleAdmin_ok() throws Exception {
        //given
        TaskEntity taskEntity = taskAddGenerate();
        TaskRequest updateTask = taskUpdateGenerate(
                "updateTaskAdmin", "update message", EPriority.LOW, ERole.ROLE_ADMIN
        );

        //when
        ResultActions resultActions = sendPutTaskResponseAndGetResultActions(updateTask, taskEntity.getIdTask());

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                     .andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
                     .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Успешное обновление задачи c ролью пользователя")
    public void givenTask_whenUpdateTasksById_thenUpdateTask_roleUser_ok() throws Exception {
        //given
        TaskEntity taskEntity = taskAddGenerate();
        TaskRequest updateTask = taskUpdateGenerate(
                "updateTaskUser", "update", EPriority.HIGH, ERole.ROLE_USER
        );

        //when
        ResultActions resultActions = sendPutTaskResponseAndGetResultActions(updateTask, taskEntity.getIdTask());

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                     .andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
                     .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }

    @Test
    @Description("Неудачная поптыка обновления задачи неавторизированным пользователем")
    public void givenTask_whenUpdateTasksById_thenUnAuthorized_fail() throws Exception {
        //given
        TaskEntity taskEntity = taskAddGenerate();
        TaskRequest updateTask = taskUpdateGenerate(
                "updateTaskUser", "update message", EPriority.LOW, ERole.ROLE_USER
        );

        //when
        ResultActions resultActions = sendPutTaskResponseAndGetResultActions(updateTask, taskEntity.getIdTask());

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
