package com.managerPass.test.user.deleteUsersId;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование удаления пользователя")
public class DeleteUserTest extends DeletePrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("удаление добавленного пользователя")
    public void deleteUsers_Admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        deleteByIdUsers(user.getIdUser()).andExpect(status().is2xxSuccessful());

        //then
        assert !userProvider.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("удаление несуществующего пользователя")
    public void deleteUsers_Admin_fail() throws Exception {
        //given
        userGenerate();

        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Удаление пользователя")
    public void deleteUsers_user_fail() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        deleteByIdUsers(user.getIdUser()).andExpect(status().is4xxClientError());

        //then
        assert !userProvider.existsById(user.getIdUser());
    }

    @Test
    @Description("удаление неавторизованного пользователя")
    public void deleteUsers_unAuthorized_fail() throws Exception {
        //given
        userGenerate();

        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
