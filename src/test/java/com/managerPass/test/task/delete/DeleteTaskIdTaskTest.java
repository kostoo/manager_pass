package com.managerPass.test.task.delete;

import com.managerPass.jpa.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование удаления задач")
public class DeleteTaskIdTaskTest extends DeleteTasksPrepareTest {

    @Test
    @Description("Успешное удаление задачи по id")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void givenTask_whenDeleteTaskById_thenDeleteTask_roleAdmin_ok(){
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(taskEntity.getIdTask()).andExpect(status().is2xxSuccessful());

        //then
        assert !taskRepositoryTest.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Неудачная попытка удаления несуществующей задачи с использованием роли администратора")
    @WithMockUser(username = "kosto", roles = "ADMIN")
    public void givenNonExistentTask_whenDeleteTaskById_thenIdTaskNotExists_roleAdmin_fail() {
        //given
        TaskEntity taskEntity = taskAdminGenerate();

        //when
        deleteByIdTasks(0L).andExpect(status().is4xxClientError());

        //then
        assert taskRepositoryTest.existsById(taskEntity.getIdTask());
    }

}
