package com.managerPass.test.task.delete;

import com.managerPass.jpa.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Удаление задач")
public class DeleteTaskIdTaskTest extends DeleteTasksPrepareTest {

    @Test
    @Description("Успешное удаление задачи по id")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void givenTask_whenDeleteTaskById_thenDeleteTask_roleAdmin_ok() {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = deleteByIdTasks(taskEntity.getIdTask());

        //then
        assertStatus(resultActions, status().is2xxSuccessful());

        assert !taskRepositoryTest.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачная попытка удаления задачи, задача с таким id не существует")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void givenNonExistentTask_whenDeleteTaskById_thenIdTaskNotExists_roleAdmin_fail() {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        ResultActions resultActions = deleteByIdTasks(0L);

        //then
        assertStatus(resultActions, status().is4xxClientError());
        assert taskRepositoryTest.existsById(taskEntity.getIdTask());
    }

}
