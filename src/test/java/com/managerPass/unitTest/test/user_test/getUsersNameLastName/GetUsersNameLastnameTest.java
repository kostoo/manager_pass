package com.managerPass.unitTest.test.user_test.getUsersNameLastName;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение пользователя по имени и фамилии")
public class GetUsersNameLastnameTest extends GetUsersNameLastNamePrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение списка пользователей")
    public void getUsers_Admin_ok() throws Exception {
        UserEntity addUser1 = userGenerate("user", "test@test.ru");
        UserEntity addUser2 = userGenerate("user1", "test1@test.ru");

        getActionResult("/api/users").andExpect(jsonPath("$.*", hasSize(2)))
                                               .andExpect(jsonPath("$[0].idUser").value(addUser1.getIdUser()))
                                               .andExpect(jsonPath("$[1].idUser").value(addUser2.getIdUser()))
                                               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Получение пользователя по имени и фамилии")
    public void getUsersNameLastName_Admin_ok() throws Exception {
        UserEntity addUser1 = userGenerate("user", "test@test.ru", "name", "last name");
        UserEntity addUser2 = userGenerate("user1", "test1@test.ru", "name", "last name");
        userGenerate("user1", "test1@test.ru", "nik", "nest");

        getActionResult(
                "/api/users?name={name}&lastName={lastName}", addUser1.getName(), addUser1.getLastName()
        ).andExpect(jsonPath("$.*", hasSize(2)))
         .andExpect(jsonPath("$[0].idUser").value(addUser1.getIdUser()))
         .andExpect(jsonPath("$[0].name").value(addUser1.getName()))
         .andExpect(jsonPath("$[0].lastName").value(addUser1.getLastName()))
         .andExpect(jsonPath("$[1].idUser").value(addUser2.getIdUser()))
         .andExpect(jsonPath("$[1].name").value(addUser2.getName()))
         .andExpect(jsonPath("$[1].lastName").value(addUser2.getLastName()))
         .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Получение пользователя по имени")
    public void getUsersName_Admin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResult(
                "/api/users?name={name}", user.getName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
         .andExpect(jsonPath("$[0].name").value(user.getName()))
         .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
         .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Получение пользователя по фамилии")
    public void getUsersLastName_Admin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResult(
                "/api/users?lastName={lastName}", user.getLastName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
         .andExpect(jsonPath("$[0].name").value(user.getName()))
         .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
         .andExpect(status().isOk());
    }

    @Test
    @Description("Получение списка пользователей")
    public void getUsers_unAuthorized_fail() throws Exception {
        UserEntity addUser1 = userGenerate("user", "test@test.ru");
        UserEntity addUser2 = userGenerate("user1", "test1@test.ru");

        getActionResult("/api/users").andExpect(status().isUnauthorized());
    }
}
