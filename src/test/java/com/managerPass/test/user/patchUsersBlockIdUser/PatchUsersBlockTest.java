package com.managerPass.test.user.patchUsersBlockIdUser;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование блокировки пользователя")
public class PatchUsersBlockTest extends PatchUsersBlockIdUserPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Блокировка пользователя")
    public void patchBlockUser_Admin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = sendPatchAndGetResultActions(
                "/api/users/block?idUser={idUser}", user.getIdUser()
        );

        //then
        resultActions.andExpect(jsonPath("$.isAccountNonBlock").value(false))
                     .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                     .andExpect(status().isOk());
    }

    @Test
    @Description("Блокировка пользователя")
    public void patchBlockUser_UnAuthorized_fail() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = sendPatchAndGetResultActions(
                "/api/users/block?idUser={idUser}", user.getIdUser()
        );

        //then
        resultActions.andExpect(status().isUnauthorized());
    }
}
