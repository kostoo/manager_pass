package com.managerPass.test.user.get.userId;

import com.managerPass.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение пользователя по id")
public class GetUsersTest extends GetUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение пользователя по id")
    public void givenUser_whenGetUsersIdUser_thenGetUser_roleAdmin_ok() {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResultIdUser(user.getIdUser());

        //then
        expectAll(resultActions,
                status().is2xxSuccessful(),
                jsonPath("$.name").value(user.getName()),
                jsonPath("$.lastName").value(user.getLastName()),
                jsonPath("$.username").value(user.getUsername()),
                jsonPath("$.idUser").value(user.getIdUser())
        );

        assert userRepositoryTest.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное получение пользователя по id, пользователь не существует")
    public void whenGetUsersIdUser_thenIdUserIsNotExists_roleAdmin_fail() {
        //when
        ResultActions resultActions = getActionResultIdUser(0L);

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }

    @Test
    @Description("Неудачное получение пользователя по id, пользователь не авторизован")
    public void whenGetUsersIdUser_thenUnAuthorized_fail() {
        //when
        ResultActions resultActions = getActionResultIdUser(0L);

        //then
        assertStatus(resultActions, status().is4xxClientError());
    }
}
