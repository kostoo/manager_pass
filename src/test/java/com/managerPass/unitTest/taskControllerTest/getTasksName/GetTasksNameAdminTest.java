package com.managerPass.unitTest.taskControllerTest.getTasksName;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование получения задачи по параметру названия задачи")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class GetTasksNameAdminTest extends GetTasksNamePrepareTest{

    @Test
    @Description("Получение задач по названию ok")
    public void getTasksNameWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        getActionResult("/api/tasks?name={name}", taskEntity.getName()
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$[0].idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$[0].name").value(taskEntity.getName()));
    }

    @Test
    @Description("Получение задач по названию fail")
    public void getTasksNameNullWithAdmin_fail() throws Exception {
        getActionsTasksName( "").andExpect(status().is4xxClientError());
    }

}
