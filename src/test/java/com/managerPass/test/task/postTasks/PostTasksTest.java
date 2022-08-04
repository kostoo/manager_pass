package com.managerPass.test.task.postTasks;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.response.TaskResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование добавления задачи")
public class PostTasksTest extends PostTasksPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное добавление задачи c обязательными полями с правами администратора")
    public void addTasksRequiredParameters_Admin_ok() throws Exception {
        //given
        TaskResponse taskResponse = taskDbFalseGenerate("test task", "m", null, ERole.ROLE_ADMIN);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskResponse);

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                     .andExpect(jsonPath("$.name").value(taskResponse.getName()));
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное добавление задачи со всеми параметрами")
    public void addTasksAllParam_Admin_ok() throws Exception {
        //given
        TaskResponse taskResponse = taskDbFalseGenerate("task","m", EPriority.LOW, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskResponse);

        //then
        resultActions.andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Успешное добавление задачи")
    public void addTasks_user_fail() throws Exception {
        //given
        TaskResponse taskResponse = taskDbFalseGenerate("task","m", EPriority.LOW, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskResponse);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Добавление задачи c неавторизированным пользователем")
    public void addTasks_unAuthorized_fail() throws Exception {
        //given
        TaskResponse taskResponse = taskDbFalseGenerate("task","m", EPriority.LOW, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskResponse);

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}