package com.managerPass.unitTest.taskControllerTest.getTasksUsers;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение задачи по определенному авторизированному пользователю с параметрами")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class GetTasksUsersAdminTest extends GetTasksUsersPrepareTest {

    @Test
    @Description("Получение задач по авторизированному пользователю")
    public void getTasksUsersAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult("/api/tasks/users"
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$[0].idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$[0].name").value(taskEntity.getName()));
    }

    @Test
    @Description("Получение задач по определенному приоритету")
    public void getTasksByIdPriorityWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult("/api/tasks?idPriority={idPriority}", taskEntity.getPriority().getId()
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$[0].idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$[0].name").value(taskEntity.getName()));
    }

    @Test
    @Description("Получение задач по промежутку дат")
    public void getTasksByDateAfterBeforeWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult("/api/tasks?startDateTime={startDateTime}&dateTimeFinish={dateTimeFinish}",
                taskEntity.getDateTimeStart().minusMinutes(1), taskEntity.getDateTimeFinish().plusMinutes(1)
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$[0].idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$[0].name").value(taskEntity.getName()));
    }

    @Test
    @Description("Получение задач по несуществующему id приоритета")
    public void getTasksByIdPriorityWithAdmin_fail() throws Exception {
        taskAdminGenerate();

        getActionResult("/api/tasks?idPriority={idPriority}", 0
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Получение задач по дате старта")
    public void getTasksByDateAfterWithAdmin_fail() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult(
                "/api/tasks?startDateTime={startDateTime}", taskEntity.getDateTimeStart().minusMinutes(1)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Получение задач по определенному приоритету")
    public void getTasksByDateWithAdmin_fail() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult(
                "/api/tasks?startDateTime={startDateTime}", taskEntity.getDateTimeStart().minusMinutes(1)
        ).andExpect(status().is4xxClientError());
    }
}
