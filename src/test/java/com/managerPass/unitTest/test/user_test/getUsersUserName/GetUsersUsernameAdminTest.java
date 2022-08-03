package com.managerPass.unitTest.test.user_test.getUsersUserName;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "kosto", roles = "ADMIN")
@Description("Получение пользователя по username")
public class GetUsersUsernameAdminTest extends GetUsersUsernamePrepareTest {

    @Test
    @Description("Получение пользователя по username")
    public void getUsersUserNameWithAdmin_ok() throws Exception {
        UserEntity user = userGenerate();

        getActionResultUserName(user.getUsername()
        ).andExpect(jsonPath("$.idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(status().isOk());
    }
}
