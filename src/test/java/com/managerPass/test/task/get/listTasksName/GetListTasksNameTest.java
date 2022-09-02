package com.managerPass.test.task.get.listTasksName;

import com.managerPass.jpa.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование получения задачи по названию задачи")
public class GetListTasksNameTest extends GetListTasksNamePrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение задач по названию c использованием роли администратором")
    public void givenTask_whenGetTasksName_thenGetListOfTasks_roleAdmin_ok() {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = getActionResult("/api/tasks?name={name}", taskEntity.getName());

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                     .andExpect(jsonPath("$[0].idTask").value(taskEntity.getIdTask()))
                     .andExpect(jsonPath("$[0].name").value(taskEntity.getName()));
    }

    @Test
    @Description("Неудачная попытка получения задач по названию, пользователь не авторизован")
    public void givenTaskUnAuthorized_whenGetTasksName_thenUnAuthorized_fail() {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = getActionResult("/api/tasks?name={name}", taskEntity.getName());

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
