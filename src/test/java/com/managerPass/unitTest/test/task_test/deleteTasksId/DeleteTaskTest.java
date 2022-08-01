package com.managerPass.unitTest.test.task_test.deleteTasksId;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Удаления задач")
public class DeleteTaskTest extends DeleteTasksPrepareTest {

    @Test
    @Description("Успешное удаление задачи по id задачи с ролью админа")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void deleteTasksIdTasks_Admin_ok() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().is2xxSuccessful());

        assert !taskProvider.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачное удаление несуществующей задачи с ролью администратора")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void deleteTasks_Admin_fail() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        deleteByIdTasks(0L).andExpect(status().is4xxClientError());

        assert taskProvider.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачное удаление задачи по id неавторизированного")
    public void deleteTasks_UnAuthorized_fail() throws Exception {
        TaskEntity taskEntity = taskAdminGenerate();

        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().isUnauthorized());

        assert taskProvider.existsById(taskEntity.getIdTask());
    }
}
