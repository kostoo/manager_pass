package com.managerPass.unitTest.test.user_test.postUsersBlockIdUser;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование блокировки пользователя")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class PostUsersBlockAdminTest extends PostUsersBlockIdUserPrepareTest {

    @Test
    @Description("Блокировка пользователя")
    public void patchBlockUserWithAdmin_ok() throws Exception {
    UserEntity user = userGenerate();

        sendPatchAndGetResultActions(
                "/api/users/block?idUser={idUser}", user.getIdUser()
        ).andExpect(jsonPath("$.isAccountNonBlock").value(false))
         .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
         .andExpect(status().isOk());
    }

    @Test
    @Description("Блокировка пользователя без параметра isBlock")
    public void patchBlockUserWithAdmin_fail() throws Exception {
        UserEntity user = userGenerate();

        sendPatchAndGetResultActions(
                "/api/users/block?idUser={idUser}", user.getIdUser()
        ).andExpect(status().is4xxClientError());

        assert userProvider.existsByUsername(user.getUsername());
    }
}
