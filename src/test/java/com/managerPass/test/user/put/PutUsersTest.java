package com.managerPass.test.user.put;

import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.payload.request.user.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Обновление пользователя")
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
        expectAll(resultActions, status().is2xxSuccessful(), jsonPath("$.username").value(user.getUsername()));

        assert userRepositoryTest.existsByUsername(updateUser.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное обновление пользователя, email уже существует")
    public void givenUserEmailAlreadyExists_whenUpdateUsers_thenUpdateEntityHasExistsEmail_roleAdmin_fail() {
        //given
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setEmail(userAlreadyAddDb.getEmail());

        //when
        ResultActions resultActions = sendPutAndGetResultActions(updateUser);

        //then

        assertStatus(resultActions, status().is4xxClientError());
        assert userRepositoryTest.existsByUsername(updateUser.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное обновление пользователя, username уже используется")
    public void givenUserUsernameAlreadyExists_whenUpdateUsers_thenUpdateEntityHasExistsUsername_roleAdmin_fail() {
        //given
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        //when
        ResultActions resultActions = sendPutAndGetResultActions(updateUser);

        //then
        assertStatus(resultActions, status().is4xxClientError());
        assert userRepositoryTest.existsByUsername(updateUser.getUsername());
    }
}
