package com.managerPass.test.user.patchUsersUnBlockIdUser;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование разблокировки пользователя")
public class PatchUsersUnBlockTest extends PatchUsersUnBlockPrepareTest{

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Разблокировка пользователя")
    public void patchUnBlockUser_Admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = sendPatchAndGetResultActions(
            "/api/users/unblock?idUser={idUser}", user.getIdUser()
        );

        //then
        resultActions.andExpect(jsonPath("$.isAccountNonBlock").value(false))
                     .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                     .andExpect(status().isOk());
    }

    @Test
    @Description("Разблокировка пользователя")
    public void patchUnblockUser_UnAuthorized_fail() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = sendPatchAndGetResultActions(
                "/api/users/unblock?idUser={idUser}", user.getIdUser()
        );

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
