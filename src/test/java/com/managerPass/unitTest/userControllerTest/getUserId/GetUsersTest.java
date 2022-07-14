package com.managerPass.unitTest.userControllerTest.getUserId;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "kosto", roles = "ADMIN")
public class GetUsersTest extends GetUsersPrepareTest {

    @Test
    @Description("Получение пользователя по id")
    public void getUsersIdUserWithAdmin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResultIdUser( user.getIdUser()
        ).andExpect(jsonPath("$.name").value(user.getName()))
        .andExpect(jsonPath("$.lastName").value(user.getLastName()))
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
        .andExpect(status().isOk());

        assert userProvider.existsById(user.getIdUser());
    }

    @Test
    @Description("Получение пользователя по id не существующего пользователя")
    public void getUsersIdUserWithAdmin_fail() throws Exception {
        getActionResultIdUser(0L).andExpect(status().is4xxClientError());
    }
}
