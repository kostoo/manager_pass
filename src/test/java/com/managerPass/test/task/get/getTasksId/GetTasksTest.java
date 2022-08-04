package com.managerPass.test.task.get.getTasksId;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение задач по id")
public class GetTasksTest extends GetTasksPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Получение задачи по id")
     public void getTasksIdTask_Admin_ok() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = getTasksActionResult(taskEntity.getIdTask());

        //then
        resultActions.andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
                     .andExpect(jsonPath("$.name").value(taskEntity.getName()))
                     .andExpect(jsonPath("$.message").value(taskEntity.getMessage()))
                     .andExpect(status().is2xxSuccessful());


    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Получение задачи по несуществующему id")
    public void getTasksIdTask_Admin_fail() throws Exception {
        //given
        taskAdminGenerate();

        //when
        ResultActions resultActions = getTasksActionResult(0L);

        //then
        resultActions.andExpect(status().is4xxClientError());

    }

    @Test
    @Description("Получение задачи по id")
    public void getTasksIdTask_UnAuthorized_ok() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = getTasksActionResult(taskEntity.getIdTask());

        //then
        resultActions.andExpect(status().is4xxClientError());
    }
}