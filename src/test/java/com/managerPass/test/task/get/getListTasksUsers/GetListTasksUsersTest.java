package com.managerPass.test.task.get.getListTasksUsers;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение задачи по определенному авторизированному пользователю с параметрами")
public class GetListTasksUsersTest extends GetTasksUsersPrepareTest {

    @Test
    @WithMockUser(username = "nikita", roles = "USER")
    @Description("Успешное получение задач по авторизированному пользователю")
    public void givenUserEntityAndUserEntity_whenGetTasksUsers_thenListTasks_Admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");
        UserEntity anotherUser = userGenerate("test","test@email");

        TaskEntity addEntity = taskAdminGenerate("name","message", EPriority.LOW, user);
        TaskEntity addEntity1 = taskAdminGenerate("name1","message1", EPriority.HIGH, user);
        TaskEntity addEntity2 = taskAdminGenerate("name2","message2", EPriority.MEDIUM, user);
        taskAdminGenerate("name2","message2", EPriority.HIGH, anotherUser);

        //when
        ResultActions resultActions = getActionResult("/api/tasks/users");

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                     .andExpect(jsonPath("$.*", hasSize(3)))
                     .andExpect(jsonPath("$[0].idTask").value(addEntity.getIdTask()))
                     .andExpect(jsonPath("$[1].idTask").value(addEntity1.getIdTask()))
                     .andExpect(jsonPath("$[2].idTask").value(addEntity2.getIdTask()));
    }

    @Test
    @WithMockUser(username = "nikita", roles = "USER")
    @Description("Успешное получение задач по определенному приоритету с использованием роли администратора")
    public void givenTaskEntityAndUser_whenGetTasksByIdPriority_thenListTasks_Admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name","message", EPriority.LOW, user);
        TaskEntity addEntity1 = taskAdminGenerate("name1","message1", EPriority.LOW, user);

        //when
        ResultActions resultActions = getActionResult(
                "/api/tasks/users?namePriority={namePriority}", EPriority.LOW
        );

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                     .andExpect(jsonPath("$.*", hasSize(2)))
                     .andExpect(jsonPath("$[0].idTask").value(addEntity.getIdTask()))
                     .andExpect(jsonPath("$[1].idTask").value(addEntity1.getIdTask()));
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Успешное получение задач по промежутку дат от и до с использованием роли администратора")
    public void givenTaskEntityAndUser_whenGetTasksByDateAfterBefore_thenListTaskEntity_admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name","message", EPriority.LOW, user);
        TaskEntity addEntity1 = taskAdminGenerate("name1","message1", EPriority.LOW, user);

        //when
        ResultActions resultActions = getActionResult(
                "/api/tasks/users?startDateTime={startDateTime}&dateTimeFinish={dateTimeFinish}",
                addEntity.getDateTimeStart().minusMinutes(1), addEntity.getDateTimeFinish().plusMinutes(1)
        );

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                     .andExpect(jsonPath("$.*", hasSize(2)))
                     .andExpect(jsonPath("$[0].idTask").value(addEntity.getIdTask()))
                     .andExpect(jsonPath("$[1].idTask").value(addEntity1.getIdTask()));


    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Получение задач по несуществующему id приоритета")
    public void whenGetTasksByIdPriority_thenIdPriorityNotExists_admin_fail() throws Exception {
        //when
        ResultActions resultActions = getActionResult("/api/tasks/users?idPriority={idPriority}", 0);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "nikita", roles = "USER")
    @Description("Неудачная попытка получения задач по дате старта")
    public void givenUserAndTask_whenGetTasksByDateAfter_thenDateAfterIsInvalidParam_admin_fail() throws Exception {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name","message", EPriority.LOW, user);

        //when
        ResultActions resultActions = getActionResult(
                "/api/tasks/users?startDateTime={startDateTime}", addEntity.getDateTimeStart().minusMinutes(1)
        );

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачная попытка получения задач по дате финиша")
    public void givenUserAndTask_whenGetTasksByDateFinish_thenDateFinishIsInvalidParam_admin_fail() throws Exception {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name","message", EPriority.LOW, user);

        //when
        ResultActions resultActions =  getActionResult(
              "/api/tasks/users?finishDateTime={finishDateTime}", addEntity.getDateTimeFinish().plusMinutes(1)
        );

        //then
        resultActions.andExpect(status().is4xxClientError());
    }
}
