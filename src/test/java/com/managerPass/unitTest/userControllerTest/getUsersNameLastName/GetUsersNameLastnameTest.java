package com.managerPass.unitTest.userControllerTest.getUsersNameLastName;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "kosto", roles = "ADMIN")
public class GetUsersNameLastnameTest extends GetUsersNameLastNamePrepareTest {

    @Test
    @Description("Получение списка пользователей")
    public void getUsersWithAdmin_ok() throws Exception {
        UserEntity addUser1 = userGenerate("user","test@test.ru");
        UserEntity addUser2 = userGenerate("user1","test1@test.ru");

        getActionResult("/api/users"
        ).andExpect(jsonPath("$[0].idUser").value(addUser1.getIdUser()))
        .andExpect(jsonPath("$[1].idUser").value(addUser2.getIdUser()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Получение пользователя по имени и фамилии")
    public void getUsersNameLastNameWithAdmin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResult("/api/users?name={name}&lastName={lastName}", user.getName(), user.getLastName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$[0].name").value(user.getName()))
        .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Получение пользователя по имени")
    public void getUsersNameWithAdmin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResult("/api/users?name={name}", user.getName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$[0].name").value(user.getName()))
        .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Получение пользователя по фамилии")
    public void getUsersLastNameWithAdmin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResult("/api/users?lastName={lastName}", user.getLastName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$[0].name").value(user.getName()))
        .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
        .andExpect(status().isOk());
    }
}
