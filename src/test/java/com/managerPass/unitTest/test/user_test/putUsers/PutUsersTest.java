package com.managerPass.unitTest.test.user_test.putUsers;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование обновления пользователя")
public class PutUsersTest extends PutUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Обновление user")
    public void updateUsers_Admin_ok() throws Exception {

        UserEntity user = userGenerate();
        user.setUsername("updateUserName");

        sendPutAndGetResultActions(
                user
        ).andExpect(jsonPath("$.username").value(user.getUsername()))
         .andExpect(status().is2xxSuccessful());

        assert userProvider.existsByUsername(user.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Обновление user на существующиющий email")
    public void updateUsersWithExistsEmail_Admin_fail() throws Exception {
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setEmail(userAlreadyAddDb.getEmail());

        sendPutAndGetResultActions(updateUser).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(updateUser.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Обновление user")
    public void updateUsersWithExistsUserName_Admin_ok() throws Exception {
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        sendPutAndGetResultActions(updateUser).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(updateUser.getUsername());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Обновление user на существующиющий username")
    public void updateUsersWithExistsUserName_Admin_fail() throws Exception {
        UserEntity userAlreadyAddDb = userGenerate("userAlreadyDB", "test0@test.ru");

        UserEntity updateUser = userGenerate("username", "test1@test.ru");
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        sendPutAndGetResultActions(updateUser).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(updateUser.getUsername());
    }
}
