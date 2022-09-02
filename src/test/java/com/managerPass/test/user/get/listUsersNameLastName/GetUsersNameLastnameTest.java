package com.managerPass.test.user.get.listUsersNameLastName;

import com.managerPass.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение пользователя по имени и фамилии")
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
        resultActions.andExpect(jsonPath("$.*", hasSize(2)))
                     .andExpect(jsonPath("$[0].idUser").value(addUser1.getIdUser()))
                     .andExpect(jsonPath("$[1].idUser").value(addUser2.getIdUser()))
                     .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение списка пользователя по имени и фамилии")
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
        resultActions.andExpect(jsonPath("$.*", hasSize(2)))
                     .andExpect(jsonPath("$[0].idUser").value(addUser1.getIdUser()))
                     .andExpect(jsonPath("$[1].idUser").value(addUser2.getIdUser()))
                     .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение пользователя по имени")
    public void givenUser_whenGetUsersName_thenGetListOfUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResult(
                "/api/users?name={name}", user.getName()
        );

        //then
        resultActions.andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
                     .andExpect(jsonPath("$[0].name").value(user.getName()))
                     .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
                     .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение списка пользователей по фамилии")
    public void givenUser_whenGetUsersLastName_thenGetListOfUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResult(
                "/api/users?lastName={lastName}", user.getLastName()
        );

        //then
        resultActions.andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
                     .andExpect(jsonPath("$[0].name").value(user.getName()))
                     .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
                     .andExpect(status().isOk());
    }

    @Test
    @Description("Неудачное получение списка пользователей неавторизованным пользователем")
    public void whenGetUsers_thenUnAuthorized_fail() {
        //when
        ResultActions resultActions = getActionResult("/api/users");

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
