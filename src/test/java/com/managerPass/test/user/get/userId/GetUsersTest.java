package com.managerPass.test.user.get.userId;

import com.managerPass.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Description("Получение задачи пользователя по id")
public class GetUsersTest extends GetUsersPrepareTest {

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Успешное получение пользователя по id")
    public void givenUser_whenGetUsersIdUser_thenGetUser_roleAdmin_ok() throws Exception {
        //given
        UserEntity user = userGenerate();

        //when
        ResultActions resultActions = getActionResultIdUser(user.getIdUser());

        //then
        resultActions.andExpect(jsonPath("$.name").value(user.getName()))
                     .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                     .andExpect(jsonPath("$.username").value(user.getUsername()))
                     .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                     .andExpect(status().isOk());

        assert userRepositoryTest.existsById(user.getIdUser());
    }

    @Test
    @WithMockUser(username = "kosto", roles = "ADMIN")
    @Description("Неудачное получение пользователя по id не существующего пользователя")
    public void whenGetUsersIdUser_thenIdUserIsNotExists_roleAdmin_fail() throws Exception {
        //when
        ResultActions resultActions = getActionResultIdUser(0L);

        //then
        resultActions.andExpect(status().is4xxClientError());

    }

    @Test
    @Description("Неудачное получение пользователя по id неавторизованным пользователем")
    public void whenGetUsersIdUser_thenUnAuthorized_fail() throws Exception {
        //when
        ResultActions resultActions = getActionResultIdUser(0L);

        //then
        resultActions.andExpect(status().is4xxClientError());
    }
}
