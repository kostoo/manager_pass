package com.managerPass.test.task.get.task_id;

import com.managerPass.jpa.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение задачи по id")
public class GetTasksTest extends GetTasksPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение задачи по id")
     public void givenTask_whenGetTasksById_thenGetTasks_roleAdmin_ok() {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = getTasksActionResult(taskEntity.getIdTask());

        //then
        expectAll(
                resultActions,
                jsonPath("$.idTask").value(taskEntity.getIdTask()),
                jsonPath("$.name").value(taskEntity.getName()),
                jsonPath("$.message").value(taskEntity.getMessage()),
                status().is2xxSuccessful()
        );
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное получение задачи по id, задачи с таким id не существует")
    public void whenGetTasksIdTask_thenIdTasksNotExists_roleAdmin_fail() {
        //when
        ResultActions resultActions = getTasksActionResult(0L);

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @Description("Неудачное получение задачи по id, пользователь не авторизован")
    public void givenTaskUnAuthorized_whenGetTasksIdTask_thenUnAuthorized_fail() {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = getTasksActionResult(taskEntity.getIdTask());

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }
}
