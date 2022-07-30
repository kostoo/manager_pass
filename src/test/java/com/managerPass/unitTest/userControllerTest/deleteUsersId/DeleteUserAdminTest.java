package com.managerPass.unitTest.userControllerTest.deleteUsersId;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Тестирование удаление пользователя")
@WithMockUser(username = "kosto", roles = "ADMIN")
public class DeleteUserAdminTest extends DeletePrepareTest {

    @Test
    @Description("удаление добавленного пользователя")
    public void deleteUsersWithAdmin_ok() throws Exception {
        UserEntity user = userGenerate();

        deleteByIdUsers(user.getIdUser()).andExpect(status().is2xxSuccessful());

        assert !userProvider.existsById(user.getIdUser());
    }

    @Test
    @Description("удаление несуществующего пользователя")
    public void deleteUsersWithAdmin_fail() throws Exception {
        userGenerate();

        deleteByIdUsers(0L).andExpect(status().is4xxClientError());
    }
}
