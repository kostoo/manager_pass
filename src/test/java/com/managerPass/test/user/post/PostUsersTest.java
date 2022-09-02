package com.managerPass.test.user.post;

import com.managerPass.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Добавление пользователя")
public class PostUsersTest extends PostUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное добавление пользователя с обязательными параметрами")
    public void givenUser_whenPostUsers_thenAddUsers_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate(
                "name", "lastname", "username", "email@email", false
        );

        //when
        ResultActions resultActions = sendPostUsersAndGetResultActions(user);

        //then
        assertStatus(resultActions, status().is2xxSuccessful());
        assert userProvider.isUserExistByUsername(user.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное добавление пользователя, username уже используется")
    public void givenUserUsernameAlreadyExists_whenAddUsers_whenExistsUserName_fail() {
        //given
        userGenerate("name", "lastname", "username", "email@email", true);
        UserEntity userAlreadyExists = userGenerate(
                "name", "lastname", "username", "email1@email", false
        );

        //when
        ResultActions resultActions = sendPostUsersAndGetResultActions(userAlreadyExists);

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное добавление пользователя, email уже используется")
    public void givenUserEmailAlreadyExists_whenAddUsers_thenEmailExists_fail() {
        //given
        userGenerate("name", "lastname", "username", "email@email", true);
        UserEntity userAlreadyExists = userGenerate(
                "name", "lastname", "userName", "email@email", false
        );

        //when
        ResultActions resultActions = sendPostUsersAndGetResultActions(userAlreadyExists);

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @Description("Неудачное добавление пользователя, пользователь не авторизирован")
    public void givenUserUnAuthorized_whenAddUsers_thenUnAuthorized_fail() {
        //given
        UserEntity user = userGenerate(
                "name", "lastname", "username", "email@email", false
        );

        //when
        ResultActions resultActions = sendPostUsersAndGetResultActions(user);

        //then
        assertStatus(resultActions, status().is2xxSuccessful());

        assert userRepositoryTest.existsByUsername(user.getUsername());
    }

}
