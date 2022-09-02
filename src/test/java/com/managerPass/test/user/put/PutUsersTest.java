package com.managerPass.test.user.put;

import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
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
    public void givenUser_whenUpdateUsers_thenUpdateUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate();

        UserRequest updateUser = userUpdateGenerate(
        );
        //when
        ResultActions resultActions = sendPutUserRequestAndGetResultActions(updateUser, user.getIdUser());

        //then
        resultActions.andExpect(jsonPath("$.username").value(user.getUsername()))
                     .andExpect(status().is2xxSuccessful());

        assert userRepositoryTest.existsByUsername(updateUser.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное обновление пользователя существующиющий email")
    public void givenUser_whenUpdateUsers_thenUpdateEntityHasExistsEmail_roleAdmin_fail() {
        //given
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setEmail(userAlreadyAddDb.getEmail());

        //when
        ResultActions resultActions = sendPutAndGetResultActions(updateUser);

        //then

        resultActions.andExpect(status().is4xxClientError());
        assert userRepositoryTest.existsByUsername(updateUser.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Обновление user на существующиющий username")
    public void givenUser_whenUpdateUsers_thenUpdateEntityHasExistsUsername_roleAdmin_fail() {
        //given
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        //when
        sendPutAndGetResultActions(updateUser).andExpect(status().is4xxClientError());

        //then
        assert userRepositoryTest.existsByUsername(updateUser.getUsername());
    }
}
