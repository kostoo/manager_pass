package com.managerPass.test.user.get.userUsername;

import com.managerPass.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение пользователя по username")
public class GetUsersUsernameTest extends GetUsersUsernamePrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение пользователя по username")
    public void givenUser_whenGetUsersUserName_thenGetListOfUser_roleAdmin_ok() {
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
    @Description("Неудачное получение пользователя по username неавторизованным пользователем")
    public void givenUser_whenGetUsersUserName_thenUnAuthorized_fail() {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResultUserName(user.getUsername());

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
