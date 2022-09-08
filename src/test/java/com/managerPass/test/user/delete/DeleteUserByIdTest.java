package com.managerPass.test.user.delete;

import com.managerPass.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Удаление пользователя")
public class DeleteUserByIdTest extends DeleteUserByIdPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное удаление пользователя")
    public void givenUser_whenDeleteUsersById_thenDeleteUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = deleteByIdUsers(user.getIdUser());

        //then

        assertStatus(resultActions, status().is2xxSuccessful());
        assert !userRepositoryTest.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачная попытка удаления пользователя, пользователя с данным id не существует")
    public void whenDeleteUsersById_thenIdUserNotExists_roleAdmin_fail() {
        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @Description("Неудачная попытка удаления пользователя, пользователь не авторизован")
    public void givenUserUnAuthorized_whenDeleteUsersById_thenUnAuthorized_fail() {
        //given
        userGenerate();

        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        assertStatus(resultActions, status().isUnauthorized());
    }
}
