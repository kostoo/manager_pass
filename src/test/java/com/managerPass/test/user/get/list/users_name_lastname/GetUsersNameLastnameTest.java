package com.managerPass.test.user.get.list.users_name_lastname;

import com.managerPass.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение списка пользователей по имени и фамилии")
public class GetUsersNameLastnameTest extends GetUsersNameLastNamePrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение списка пользователей")
    public void givenUsers_whenGetUsers_thenGetListOfUser_roleAdmin_ok() {
        //given
        UserEntity addUser1 = userGenerate("user", "test@test.ru");
        UserEntity addUser2 = userGenerate("user1", "test1@test.ru");

        //when
        ResultActions resultActions = getActionResult("/api/users");

        //then
        expectAll(
                  resultActions,
                  jsonPath("$.*", hasSize(2)),
                  jsonPath("$[0].idUser").value(addUser1.getIdUser()),
                  jsonPath("$[1].idUser").value(addUser2.getIdUser()),
                  status().is2xxSuccessful()
        );
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение списка пользователей по имени и фамилии")
    public void givenUsers_whenGetUsersNameLastName_thenGetListOfUser_roleAdmin_ok() {
        //given
        UserEntity addUser1 = userGenerate("user", "test@test.ru", "name", "last name");
        UserEntity addUser2 = userGenerate("user1", "test1@test.ru", "name", "last name");
        userGenerate("user1", "test1@test.ru", "nik", "nest");

        //when
        ResultActions resultActions = getActionResult(
                "/api/users?name={name}&lastName={lastName}", addUser1.getName(), addUser1.getLastName()
        );

        //then
        expectAll(
                  resultActions,
                  jsonPath("$.*", hasSize(2)),
                  jsonPath("$[0].idUser").value(addUser1.getIdUser()),
                  jsonPath("$[1].idUser").value(addUser2.getIdUser()),
                  status().is2xxSuccessful()
        );
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение списка пользователей по имени")
    public void givenUsers_whenGetUsersName_thenGetListOfUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResult(
                "/api/users?name={name}", user.getName()
        );

        //then
        expectAll(
                  resultActions,
                  status().is2xxSuccessful(),
                  jsonPath("$[0].idUser").value(user.getIdUser()),
                  jsonPath("$[0].name").value(user.getName()),
                  jsonPath("$[0].lastName").value(user.getLastName())
        );
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение списка пользователей по фамилии")
    public void givenUsers_whenGetUsersLastName_thenGetListOfUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResult(
                "/api/users?lastName={lastName}", user.getLastName()
        );

        //then
        expectAll(
                  resultActions,
                  status().is2xxSuccessful(),
                  jsonPath("$[0].idUser").value(user.getIdUser()),
                  jsonPath("$[0].name").value(user.getName()),
                  jsonPath("$[0].lastName").value(user.getLastName())
        );
    }

    @Test
    @Description("Неудачное получение списка пользователей, пользователь не авторизован")
    public void whenGetUsers_thenUnAuthorized_fail() {
        //when
        ResultActions resultActions = getActionResult("/api/users");

        //then
        assertStatus(resultActions, status().isUnauthorized());
    }
}
