package com.managerPass.test.task.get.listTasksName;

import com.managerPass.entity.TaskEntity;
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
    @Description("Успешное получение задач по названию c и использованием роли администратором")
    public void givenTaskEntity_whenGetTasksName_thenGetTasks_admin_ok() throws Exception {
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
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачная попытка получения задач по пустому названию")
    public void givenTaskEntity_whenGetTasksName_thenNull_Admin_fail() throws Exception {
        //when
        ResultActions resultActions = getActionsTasksName((Object) null);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Неудачная попытка получения задач по названию с помощью неавторизированного пользователя")
    public void givenTaskEntity_whenGetTasksName_thenUnAuthorized_fail() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = getActionResult("/api/tasks?name={name}", taskEntity.getName());

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
