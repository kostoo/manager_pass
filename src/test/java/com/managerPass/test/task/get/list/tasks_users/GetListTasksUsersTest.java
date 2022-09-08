package com.managerPass.test.task.get.list.tasks_users;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение списка задач определенного пользователю с параметрами задач:" +
             "Epriority, dateTimeStart, dateTimeFinish")
public class GetListTasksUsersTest extends GetListTasksUsersPrepareTest {

    @Test
    @WithMockUser(username = "username", roles = "USER")
    @Description("Успешное получение списка задач определенного пользователя")
    public void givenTasksOfUsers_whenGetTasksUsers_thenGetListTasksOfUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");
        UserEntity anotherUser = userGenerate("test", "test@email");

        TaskEntity addEntity = taskAdminGenerate("name", "message", EPriority.LOW, user);
        TaskEntity addEntity1 = taskAdminGenerate("name1", "message1", EPriority.HIGH, user);
        TaskEntity addEntity2 = taskAdminGenerate("name2", "message2", EPriority.MEDIUM, user);
        taskAdminGenerate("name2", "message2", EPriority.HIGH, anotherUser);

        //when
        ResultActions resultActions = getActionResult("/api/tasks/users");

        //then
        expectAll(
                  resultActions,
                  status().is2xxSuccessful(),
                  jsonPath("$.*", hasSize(3)),
                  jsonPath("$[0].idTask").value(addEntity.getIdTask()),
                  jsonPath("$[0].idTask").value(addEntity.getIdTask()),
                  jsonPath("$[1].idTask").value(addEntity1.getIdTask()),
                  jsonPath("$[2].idTask").value(addEntity2.getIdTask())
        );
    }

    @Test
    @WithMockUser(username = "username", roles = "USER")
    @Description("Успешное получение списка задач пользователя по названию приоритета")
    public void givenTasksOfUserPriorityLow_whenGetTasksByNamePriority_thenGetListTasksOfUser_roleUser_ok() {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name", "message", EPriority.LOW, user);
        TaskEntity addEntity1 = taskAdminGenerate("name1", "message1", EPriority.LOW, user);

        //when
        ResultActions resultActions = getActionResult(
                "/api/tasks/users?namePriority={namePriority}", EPriority.LOW
        );

        //then

        expectAll(
                  resultActions,
                  status().is2xxSuccessful(),
                  jsonPath("$.*", hasSize(2)),
                  jsonPath("$[0].idTask").value(addEntity.getIdTask()),
                  jsonPath("$[1].idTask").value(addEntity1.getIdTask())
        );

    }

    @Test
    @WithMockUser(username = "username", roles = "USER")
    @Description("Успешное получение списка задач с параметрами dateTimeStart, dateTimeFinish")
    public void givenTasksOfUser_whenGetTasksByDateStartFinish_thenGetListTasksOfUserByDate_roleUser_ok() {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name", "message", EPriority.LOW, user);
        TaskEntity addEntity1 = taskAdminGenerate("name1", "message1", EPriority.LOW, user);

        //when
        ResultActions resultActions = getActionResult(
                "/api/tasks/users?startDateTime={startDateTime}&dateTimeFinish={dateTimeFinish}",
                addEntity.getDateTimeStart().minusMinutes(1), addEntity.getDateTimeFinish().plusMinutes(1)
        );

        //then
        expectAll(
                  resultActions,
                  status().is2xxSuccessful(),
                  jsonPath("$.*", hasSize(2)),
                  jsonPath("$[0].idTask").value(addEntity.getIdTask()),
                  jsonPath("$[1].idTask").value(addEntity1.getIdTask())
        );
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачная попытка получения задач, в базе не существует приоритета")
    public void whenGetTasksByIdPriority_thenIdPriorityNotExists_roleAdmin_fail() {
        //when
        ResultActions resultActions = getActionResult(
                "/api/tasks/users?idPriority={idPriority}", EPriority.MEDIUM
        );

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Неудачная попытка получения задач, введен параметр dateTimeStart, нет параметра dateTimeFinish")
    public void givenTasksOfUser_whenGetTasksByDateAfter_thenOnlyStartDateTimeIsInvalidParam_roleUser_fail() {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name", "message", EPriority.LOW, user);

        //when
        ResultActions resultActions = getActionResult(
                "/api/tasks/users?startDateTime={startDateTime}", addEntity.getDateTimeStart().minusMinutes(1)
        );

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Неудачная попытка получения задач, введен параметр dateTimeFinish, нет параметра dateTimeStart")
    public void givenTasksOfUser_whenGetTasksByDateFinish_thenOnlyDateFinishIsInvalidParam_roleAdmin_fail() {
        //given
        UserEntity user = userGenerate("username", "test@email.ru");

        TaskEntity addEntity = taskAdminGenerate("name", "message", EPriority.LOW, user);

        //when
        ResultActions resultActions =  getActionResult(
              "/api/tasks/users?finishDateTime={finishDateTime}", addEntity.getDateTimeFinish().plusMinutes(1)
        );

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }
}
