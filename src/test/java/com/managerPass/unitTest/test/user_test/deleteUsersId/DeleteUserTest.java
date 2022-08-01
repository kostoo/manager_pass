package com.managerPass.unitTest.test.user_test.deleteUsersId;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование удаления пользователя")
public class DeleteUserTest extends DeletePrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("удаление добавленного пользователя")
    public void deleteUsers_Admin_ok() throws Exception {
        UserEntity user = userGenerate();

        deleteByIdUsers(user.getIdUser()).andExpect(status().is2xxSuccessful());

        assert !userProvider.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("удаление несуществующего пользователя")
    public void deleteUsers_Admin_fail() throws Exception {
        userGenerate();

        deleteByIdUsers(0L).andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Удаление пользователя")
    public void deleteUsers_user_fail() throws Exception {
        UserEntity user = userGenerate();

        deleteByIdUsers(user.getIdUser()).andExpect(status().is4xxClientError());

        assert !userProvider.existsById(user.getIdUser());
    }

    @Test
    @Description("удаление неавторизованного пользователя")
    public void deleteUsers_unAuthorized_fail() throws Exception {
        userGenerate();

        deleteByIdUsers(0L).andExpect(status().isUnauthorized());
    }
}
