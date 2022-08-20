package com.managerPass.test.user.delete;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование удаления пользователя")
public class DeleteUserIdUserUserIdUserTest extends DeleteUserIdUserPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное удаление пользователя c использованием роли администратора")
    public void givenUser_whenDeleteUsersById_thenDeleteUser_admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        deleteByIdUsers(user.getIdUser()).andExpect(status().is2xxSuccessful());

        //then
        assert !userProvider.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("удаление несуществующего пользователя c использованием роли администратора")
    public void whenDeleteUsersById_thenIdUserNotExists_admin_fail() throws Exception {
        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "USER")
    @Description("Неудачная попытка удаления пользователя c помощью роли пользователя")
    public void givenUser_whenDeleteUsersById_then_user_fail() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        deleteByIdUsers(user.getIdUser()).andExpect(status().is4xxClientError());

        //then
        assert !userProvider.existsById(user.getIdUser());
    }

    @Test
    @Description("Неудачная попытка удаления с помощью неавторизованного пользователя")
    public void givenUser_whenDeleteUsersById_thenUnAuthorized_fail() throws Exception {
        //given
        userGenerate();

        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
