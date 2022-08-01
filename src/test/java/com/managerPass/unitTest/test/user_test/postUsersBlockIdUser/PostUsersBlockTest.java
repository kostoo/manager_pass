package com.managerPass.unitTest.test.user_test.postUsersBlockIdUser;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование блокировки пользователя")
public class PostUsersBlockTest extends PostUsersBlockIdUserPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Блокировка пользователя")
    public void patchBlockUser_Admin_ok() throws Exception {
    UserEntity user = userGenerate();

        sendPatchAndGetResultActions(
                "/api/users/block?idUser={idUser}", user.getIdUser()
        ).andExpect(jsonPath("$.isAccountNonBlock").value(false))
         .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
         .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Блокировка пользователя")
    public void patchUnBlockUser_Admin_ok() throws Exception {
        UserEntity user = userGenerate();

        sendPatchAndGetResultActions(
                "/api/users/unblock?idUser={idUser}", user.getIdUser()
        ).andExpect(jsonPath("$.isAccountNonBlock").value(false))
                .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Блокировка пользователя без параметра isBlock")
    public void patchBlockUser_Admin_fail() throws Exception {
        UserEntity user = userGenerate();

        sendPatchAndGetResultActions(
                "/api/users/block?idUser={idUser}", user.getIdUser()
        ).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(user.getUsername());
    }

    @Test
    @Description("Блокировка пользователя")
    public void patchBlockUser_UnAuthorized_fail() throws Exception {
        UserEntity user = userGenerate();

        sendPatchAndGetResultActions("/api/users/block?idUser={idUser}", user.getIdUser()).andExpect(status().isUnauthorized());
    }
}
