package com.managerPass.unitTest.test.user_test.getUserId;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение задачи пользователя по id")
public class GetUsersTest extends GetUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение пользователя по id")
    public void getUsersIdUser_Admin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResultIdUser(user.getIdUser()).andExpect(jsonPath("$.name").value(user.getName()))
                                               .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                                               .andExpect(jsonPath("$.username").value(user.getUsername()))
                                               .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                                               .andExpect(status().isOk());

        assert userProvider.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное получение пользователя по id не существующего пользователя")
    public void getUsersIdUser_Admin_fail() throws Exception {
        getActionResultIdUser(0L).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Получение пользователя по id")
    public void getUsersIdUser_unAuthorized_fail() throws Exception {
        getActionResultIdUser(0L).andExpect(status().is4xxClientError());
    }
}
