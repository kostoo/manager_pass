package com.managerPass.test.user.post;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование добавления пользователя")
public class PostUsersTest extends PostUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное добавление пользователя с обязательными параметрами")
    public void givenUser_whenPostUsers_thenAddUsers_roleAdmin_ok() throws Exception {
        //given
        UserEntity user = userGenerate(
                "name","lastname","username", "email@email", false
        );

        //when
        sendPostUsersAndGetResultActions(user).andExpect(status().is2xxSuccessful());

        //then
        assert userProvider.isUserExistByUsername(user.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное добавление пользователя с существующим username")
    public void givenUser_whenAddUsers_whenExistsUserName_fail() throws Exception {
        //given
        userGenerate("name","lastname","username", "email@email", true);
        UserEntity userAlreadyExists = userGenerate(
                "name","lastname","username", "email1@email", false
        );

        //when
        ResultActions resultActions = sendPostUsersAndGetResultActions(userAlreadyExists);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное добавление пользователя с существующим email")
    public void givenUser_whenAddUsers_thenEmailExists_fail() throws Exception {
        //given
        userGenerate("name","lastname","username", "email@email", true);
        UserEntity userAlreadyExists = userGenerate(
                "name","lastname","userName", "email@email", false
        );

        //when
        ResultActions resultActions = sendPostUsersAndGetResultActions(userAlreadyExists);

        //then
        resultActions.andExpect(status().is4xxClientError());

    }

    @Test
    @Description("Неудачное добавление пользователя, из-за неавторизированного пользователя")
    public void givenUser_whenAddUsers_thenUnAuthorized_fail() throws Exception {
        //given
        UserEntity user = userGenerate(
                "name","lastname","username", "email@email", false
        );

        //when
        sendPostUsersAndGetResultActions(user).andExpect(status().is2xxSuccessful());

        //then
        assert userRepositoryTest.existsByUsername(user.getUsername());
    }

}
