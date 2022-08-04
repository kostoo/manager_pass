package com.managerPass.test.user.postUsers;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Добавление пользователя")
public class PostUsersTest extends PostUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Добавление пользователя с обязательными параметрами")
    public void addUsers_Admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate(
                "name","lastname","username", "email@email", false
        );

        //when
        sendPostUsersAndGetResultActions(user).andExpect(status().is2xxSuccessful());

        //then
        assert userProvider.existsByUsername(user.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное Добавление пользователя с существующим username")
    public void addUsersExistsUserName_Admin_fail() throws Exception {
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
    public void addUsersExistsEmail_Admin_fail() throws Exception {
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
    @Description("Неудачное Добавление пользователя")
    public void addUsers_unAuthorized_fail() throws Exception {
        //given
        UserEntity user = userGenerate(
                "name","lastname","username", "email@email", false
        );

        //when
        sendPostUsersAndGetResultActions(user).andExpect(status().is2xxSuccessful());

        //then
        assert userProvider.existsByUsername(user.getUsername());
    }

}
