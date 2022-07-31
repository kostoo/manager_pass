package com.managerPass.unitTest.test.user_test.postUsers;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Добавление пользователя")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class PostUsersAdminTest extends PostUsersPrepareTest {

    @Test
    @Description("Добавление user")
    public void addUsersWithAdmin_ok() throws Exception {
        UserEntity user = userGenerateDbFalse();

        sendPostUsersAndGetResultActions(user).andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsername(user.getUsername());
    }

    @Test
    @Description("Добавление 2 одинаковых user с одинаковым username")
    public void addUsersExistsUserNameWithAdmin_fail() throws Exception {

        UserEntity user = userGenerateDbFalse();

        sendPostUsersAndGetResultActions(user).andExpect(status().isOk());
        sendPostUsersAndGetResultActions(user).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Добавление 2 одинаковых user с одинаковым email")
    public void addUsersExistsEmailWithAdmin_fail() throws Exception {

        UserEntity user = userGenerateDbFalse();

        sendPostUsersAndGetResultActions(user).andExpect(status().is4xxClientError());
    }

}
