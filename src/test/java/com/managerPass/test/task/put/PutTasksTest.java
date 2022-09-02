package com.managerPass.test.task.put;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.payload.request.task.TaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Обновление задачи")
public class PutTasksTest extends PutTasksPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное обновление задачи")
    public void givenTask_whenUpdateTasksById_thenUpdateTask_roleAdmin_ok() {
        //given
        TaskEntity taskEntity = taskAddGenerate();
        TaskRequest updateTask = taskUpdateGenerate(
                "updateTaskAdmin", "update message", EPriority.LOW, ERole.ROLE_ADMIN
        );

        //when
        ResultActions resultActions = sendPutTaskResponseAndGetResultActions(updateTask, taskEntity.getIdTask());

        //then
        expectAll(
                  resultActions,
                  status().is2xxSuccessful(),
                  jsonPath("$.idTask").value(taskEntity.getIdTask()),
                  jsonPath("$.name").value(taskEntity.getName())
        );

    }

    @Test
    @Description("Неудачная попытка обновления задачи, пользователь не авторизован")
    public void givenTaskUnAuthorized_whenUpdateTasksById_thenUnAuthorized_fail() {
        //given
        TaskEntity taskEntity = taskAddGenerate();
        TaskRequest updateTask = taskUpdateGenerate(
                "updateTaskUser", "update message", EPriority.LOW, ERole.ROLE_USER
        );

        //when
        ResultActions resultActions = sendPutTaskResponseAndGetResultActions(updateTask, taskEntity.getIdTask());

        //then
        assertStatus(resultActions, status().isUnauthorized());
    }
}
