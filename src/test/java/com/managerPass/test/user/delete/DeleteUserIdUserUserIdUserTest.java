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
    @Description("Успешное удаление пользователя ")
    public void givenUser_whenDeleteUsersById_thenDeleteUser_roleAdmin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        deleteByIdUsers(user.getIdUser()).andExpect(status().is2xxSuccessful());

        //then
        assert !userRepositoryTest.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачная попытка удаления несуществующего пользователя")
    public void whenDeleteUsersById_thenIdUserNotExists_roleAdmin_fail() throws Exception {
        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Неудачная попытка удаления неавторизованным пользователем")
    public void givenUser_whenDeleteUsersById_thenUnAuthorized_fail() throws Exception {
        //given
        userGenerate();

        //when
        ResultActions resultActions = deleteByIdUsers(0L);

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
