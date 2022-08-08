package com.managerPass.test.user.get.getUsersUserName;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "kosto", roles = "ADMIN")
@Description("Получение пользователя по username")
public class GetUsersUsernameTest extends GetUsersUsernamePrepareTest {

    @Test
    @Description("Получение пользователя по username")
    public void givenUser_whenGetUsersUserName_then_Admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResultUserName(user.getUsername());

        //then
        resultActions.andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                     .andExpect(jsonPath("$.username").value(user.getUsername()))
                     .andExpect(status().isOk());

    }

    @Test
    @Description("Получение пользователя по username")
    public void givenUser_whenGetUsersUserName_thenUnAuthorized_fail() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResultUserName(user.getUsername());

        //then
        resultActions.andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                     .andExpect(jsonPath("$.username").value(user.getUsername()))
                     .andExpect(status().isOk());
    }
}
