package com.managerPass.test.user.put;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование обновления пользователя")
public class PutUsersTest extends PutUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное обновление пользователя")
    public void givenUser_whenUpdateUsers_thenUpdateUser_admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();
        user.setUsername("updateUserName");

        //when
        ResultActions resultActions = sendPutAndGetResultActions(user);

        //then
        resultActions.andExpect(jsonPath("$.username").value(user.getUsername()))
                     .andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsername(user.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Обновление user на существующиющий email")
    public void givenUser_whenUpdateUsers_thenExistsEmail_admin_fail() throws Exception {
        //given
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setEmail(userAlreadyAddDb.getEmail());

        //when
        sendPutAndGetResultActions(updateUser).andExpect(status().is4xxClientError());

        //then
        assert userProvider.existsByUsername(updateUser.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Обновление user на существующиющий username")
    public void givenUser_whenUpdateUsers_thenExistsUserName_admin_fail() throws Exception {
        //given
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        //when
        sendPutAndGetResultActions(updateUser).andExpect(status().is4xxClientError());

        //then
        assert userProvider.existsByUsername(updateUser.getUsername());
    }
}
