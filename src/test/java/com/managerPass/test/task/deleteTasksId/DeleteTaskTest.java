package com.managerPass.test.task.deleteTasksId;

import com.managerPass.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование удаления задач")
public class DeleteTaskTest extends DeleteTasksPrepareTest {

    @Test
    @Description("Успешное удаление задачи по id")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void givenTaskEntity_whenDeleteTaskById_thenDeleteTask_admin_ok() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().is2xxSuccessful());

        //then
        assert !taskProvider.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачная попытка удаления несуществующей задачи с использованием роли администратора")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void givenTaskEntity_whenDeleteTaskById_thenIdTaskNotExists_admin_fail() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(0L).andExpect(status().is4xxClientError());

        //then
        assert taskProvider.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачная попытка удаления задачи с использованием неавторизированного пользователя")
    public void givenTaskEntity_whenDeleteTaskById_thenUnAuthorized_fail() throws Exception {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().isUnauthorized());

        //then
        assert taskProvider.existsById(taskEntity.getIdTask());
    }
}
