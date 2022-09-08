package com.managerPass.test.task.post;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.payload.request.task.TaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Добавление задачи")
public class PostTasksTest extends PostTasksPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное добавление задачи c обязательными полями")
    public void givenTaskRequestRequiredParam_whenAddTasksRequiredParameters_thenAddTask_roleAdmin_ok() {
        //given
        TaskRequest taskRequest = taskGenerate("test task", "m", null, ERole.ROLE_ADMIN);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then
        assert taskProvider.existTaskById(convertResultActionsToTaskResponse(resultActions).getIdTask());

        expectAll(resultActions, status().is2xxSuccessful(), jsonPath("$.name").value(taskRequest.getName()));

    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное добавление задачи со всеми параметрами")
    public void givenTaskRequestAllParam_whenAddTasksAllParam_thenAddTask_roleAdmin_ok() {
        //given
        TaskRequest taskRequest = taskGenerate("task", "m", EPriority.LOW, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then
        assert taskProvider.existTaskById(convertResultActionsToTaskResponse(resultActions).getIdTask());
        assertStatus(resultActions, status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешная попытка добавления задачи с приоритетом Low")
    public void givenTaskRequestEPriorityLow_whenAddTasks_thenAddTask_roleUser_ok() {
        //given
        TaskRequest taskRequest = taskGenerate("task", "m", EPriority.MEDIUM, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then
        assert taskProvider.existTaskById(convertResultActionsToTaskResponse(resultActions).getIdTask());
        assertStatus(resultActions, status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешная попытка добавления задачи с приоритетом MEDIUM")
    public void givenTaskRequestEPriorityMedium_whenAddTasks_thenAddTask_roleUser_ok() {
        //given
        TaskRequest taskRequest = taskGenerate("task", "m", EPriority.MEDIUM, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then
        assert taskProvider.existTaskById(convertResultActionsToTaskResponse(resultActions).getIdTask());
        assertStatus(resultActions, status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешная попытка добавления задачи с приоритетом High")
    public void givenTaskRequestEPriorityHigh_whenAddTasks_thenAddTask_roleUser_ok() {
        //given
        TaskRequest taskRequest = taskGenerate("task", "m", EPriority.HIGH, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then
        assert taskProvider.existTaskById(convertResultActionsToTaskResponse(resultActions).getIdTask());
        assertStatus(resultActions, status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачная попытка добавления задачи с пустым названием")
    public void givenTaskRequestNameTaskNull_whenAddTasks_thenNameTaskNull_fail() {
        //given
        TaskRequest taskRequest = taskGenerate(null, "m", EPriority.LOW, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then

        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачная попытка добавления задачи с пустым описанием")
    public void givenTaskRequestDescriptionNull_whenAddTasks_thenMessageNotNull_fail() {
        //given
        TaskRequest taskRequest = taskGenerate("name", null, EPriority.LOW, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @Description("Неудачная попытка добавления задачи, пользователь не авторизирован")
    public void givenTaskRequestUnAuthorized_whenAddTasks_thenUnAuthorized_fail() {
        //given
        TaskRequest taskRequest = taskGenerate("task", "m", EPriority.LOW, ERole.ROLE_USER);

        //when
        ResultActions resultActions = sendPostTasksAndGetResultActions(taskRequest);

        //then
        assertStatus(resultActions, status().isUnauthorized());
    }
}
