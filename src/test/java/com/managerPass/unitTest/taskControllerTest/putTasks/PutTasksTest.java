package com.managerPass.unitTest.taskControllerTest.putTasks;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование обновления задачи")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class PutTasksTest extends PutTasksPrepareTest {

    @Test
    @Description("Обновление задачи")
    public void updateTasksWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();
        taskEntity.setMessage("updateMessage");

        sendPutAndGetResultActions("/api/tasks", taskEntity
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }
}
