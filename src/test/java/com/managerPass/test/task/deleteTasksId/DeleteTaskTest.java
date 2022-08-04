package com.managerPass.test.task.deleteTasksId;

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
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().is2xxSuccessful());

        //then
        assert !taskProvider.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачное удаление несуществующей задачи с ролью администратора")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void deleteTasks_Admin_fail() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(0L).andExpect(status().is4xxClientError());

        //then
        assert taskProvider.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачное удаление задачи по id неавторизированного")
    public void deleteTasks_UnAuthorized_fail() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().isUnauthorized());

        //then
        assert taskProvider.existsById(taskEntity.getIdTask());
    }
}
