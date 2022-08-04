package com.managerPass.unitTest.test.user_test.postUsers;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Добавление пользователя")
public class PostUsersTest extends PostUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Добавление пользователя с обязательными параметрами")
    public void addUsers_Admin_ok() throws Exception {
        UserEntity user = userGenerate(
                "name","lastname","username", "email@email", false
        );

        sendPostUsersAndGetResultActions(user).andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsername(user.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное Добавление пользователя с существующим username")
    public void addUsersExistsUserName_Admin_fail() throws Exception {
        userGenerate("name","lastname","username", "email@email", true);
        UserEntity userAlreadyExists = userGenerate(
                "name","lastname","username", "email1@email", false
        );

        sendPostUsersAndGetResultActions(userAlreadyExists).andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное добавление пользователя с существующим email")
    public void addUsersExistsEmail_Admin_fail() throws Exception {
        userGenerate("name","lastname","username", "email@email", true);
        UserEntity userAlreadyExists = userGenerate(
                "name","lastname","userName", "email@email", false
        );

        sendPostUsersAndGetResultActions(userAlreadyExists).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Неудачное Добавление пользователя")
    public void addUsers_unAuthorized_fail() throws Exception {
        UserEntity user = userGenerate(
                "name","lastname","username", "email@email", false
        );

        sendPostUsersAndGetResultActions(user).andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsername(user.getUsername());
    }

}
