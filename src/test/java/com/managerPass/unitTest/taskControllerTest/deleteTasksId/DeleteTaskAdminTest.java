package com.managerPass.unitTest.taskControllerTest.deleteTasksId;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование удаления задач")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class DeleteTaskAdminTest extends DeleteTasksPrepareTest {

    @Test
    @Description("Удаление задачи по id")
    public void deleteTasksWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().is2xxSuccessful());

        assert !taskProvider.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Удаление несуществующей записи fail")
    public void deleteTasksWithAdmin_fail() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        deleteByIdTasks(0L).andExpect(status().is4xxClientError());

        assert taskProvider.existsById(taskEntity.getIdTask());
    }
}
