package com.managerPass.unitTest.taskProviderTest;

import com.managerPass.entity.TaskEntity;
import com.managerPass.unitTest.taskProviderTest.prepareTest.TaskProviderPrepareTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser( username = "kosto" , roles = "ADMIN")
@Description("Тестирование task провайдера c ролью admin")
public class TaskProviderTestAdmin extends TaskProviderPrepareTest {

    @Test
    @Description("Получение задач")
    public void getTasksWithAdmin_ok() throws Exception {
       taskEntityGenerate(true);

       getActionResult("/api/tasks"
       ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @Description("Получение задач по авторизированному пользователю")
    public void getTasksUsersAdmin_ok() throws Exception {
        taskEntityGenerate(true);

        getActionResult("/api/tasks/users"
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @Description("Получение задач по определенному пользователю")
    public void getTasksNameWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskEntityGenerate(true);

        getActionResult("/api/tasks?name={name}", taskEntity.getName()
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$[0].idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$[0].name").value(taskEntity.getName()));
    }

    @Test
    @Description("Добавление задачи")
    public void addTasksWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskEntityGenerate(false);

        sendPostAndGetResultActions("/api/tasks", taskEntity
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }

    @Test
    @Description("Обновление задачи")
    public void updateTasksWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskEntityGenerate(true);
        taskEntity.setMessage("updateMessage");

        sendPutAndGetResultActions("/api/tasks", taskEntity
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$.name").value(taskEntity.getName()));
    }

    @Test
    @Description("Получение задачи по id")
    public void getTasksIdTaskWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskEntityGenerate(true);

        getActionResult("/api/tasks/{idTask}", taskEntity.getIdTask()
        ).andExpect(jsonPath("$.idTask").value(taskEntity.getIdTask()))
        .andExpect(jsonPath("$.name").value(taskEntity.getName()))
        .andExpect(jsonPath("$.message").value(taskEntity.getMessage()))
        .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Description("Получение задачи по несуществующему id")
    public void getTasksIdTaskWithAdmin_fail() throws Exception {
        taskEntityGenerate(true);

        getActionResult("/api/tasks/{idTask}", 0
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Удаление задачи по id")
    public void deleteTasksWithAdmin_ok() throws Exception {
        TaskEntity taskEntity = taskEntityGenerate(true);

        deleteById("/api/tasks/{idTask}", taskEntity.getIdTask());

        assert !taskEntityRepository.existsById(taskEntity.getIdTask());
    }

    @Test
    @Description("Удаление несуществующей записи")
    public void deleteTasksWithAdmin_fail() throws Exception {
        TaskEntity taskEntity = taskEntityGenerate(true);

        deleteById("/api/tasks/{idTask}", 0L);

        assert taskEntityRepository.existsById(taskEntity.getIdTask());
    }
}
