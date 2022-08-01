package com.managerPass.unitTest.test.user_test.putUsers;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование обновления пользователя")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class PutUsersAdminTest extends PutUsersPrepareTest {

    @Test
    @Description("Обновление user")
    public void updateUsersWithAdmin_ok() throws Exception {

        UserEntity user = userGenerate();
        user.setUsername("updateUserName");

        sendPutAndGetResultActions(
                "/api/users", user
        ).andExpect(jsonPath("$.username").value(user.getUsername()))
         .andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsername(user.getUsername());
    }

    @Test
    @Description("Обновление user на существующиющий email")
    public void updateUsersWithExistsEmailWithAdmin_fail() throws Exception {
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setEmail(userAlreadyAddDb.getEmail());

        sendPutAndGetResultActions("/api/users", updateUser).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(updateUser.getUsername());
    }

    @Test
    @Description("Обновление user")
    public void updateUsersWithExistsUserNameWithAdmin_ok() throws Exception {
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        sendPutAndGetResultActions("/api/users", updateUser).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(updateUser.getUsername());
    }

    @Test
    @Description("Обновление user на существующиющий username")
    public void updateUsersWithExistsUserNameWithAdmin_fail() throws Exception {
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        sendPutAndGetResultActions("/api/users", updateUser).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(updateUser.getUsername());
    }
}
