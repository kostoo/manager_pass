package com.managerPass.unitTest.test.user_test.patchUsersUnBlockIdUser;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование разблокировки пользователя")
public class PatchUsersUnBlockTest extends PatchUsersUnBlockPrepareTest{

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Разблокировка пользователя")
    public void patchUnBlockUser_Admin_ok() throws Exception {
        UserEntity user = userGenerate();

        sendPatchAndGetResultActions(
            "/api/users/unblock?idUser={idUser}", user.getIdUser()
        ).andExpect(jsonPath("$.isAccountNonBlock").value(false))
                .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                .andExpect(status().isOk());
    }

    @Test
    @Description("Разблокировка пользователя")
    public void patchUnblockUser_UnAuthorized_fail() throws Exception {
        UserEntity user = userGenerate();

        sendPatchAndGetResultActions(
                "/api/users/unblock?idUser={idUser}", user.getIdUser()).andExpect(status().isUnauthorized()
        );
    }
}
