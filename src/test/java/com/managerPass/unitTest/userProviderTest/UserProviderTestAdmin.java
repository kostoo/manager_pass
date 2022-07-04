package com.managerPass.unitTest.userProviderTest;

import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.userProviderTest.prepareTest.UserProviderPrepareTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser( username = "kosto" , roles = "ADMIN")
@Description("Тестирование user провайдера c ролью admin")
public class UserProviderTestAdmin extends UserProviderPrepareTest {

    @Test
    @Description("Добавление user")
    public void addUsersWithAdmin_ok() throws Exception {
        UserEntity user = userEntityGenerate("kosto","test@test.ru", false);

        sendPostAndGetResultActions("/api/users", user).andExpect(status().is2xxSuccessful());

        assert userEntityRepository.existsByUsername(user.getUsername());
    }

    @Test
    @Description("Обновление user")
    public void updateUsersWithAdmin_ok() throws Exception {
        userEntityGenerate("kosto","test@test.ru", true);

        UserEntity user = userEntityGenerate("username","test1@test.ru", true);
        user.setUsername("updateUserName");

        sendPutAndGetResultActions("/api/users", user
        ).andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(status().is2xxSuccessful());

        assert userEntityRepository.existsByUsername(user.getUsername());
    }

    @Test
    @Description("Обновление user")
    public void updateUsersWithExistsUserNameWithAdmin_ok() throws Exception {
        UserEntity userAlreadyAddDb = userEntityGenerate("kosto","test@test.ru", true);

        UserEntity updateUser = userEntityGenerate("username","test1@test.ru", true);
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        sendPutAndGetResultActions("/api/users", updateUser).andExpect(status().is4xxClientError());

        assert userEntityRepository.existsByUsername(updateUser.getUsername());
    }

    @Test
    @Description("Обновление user на существующиющий username")
    public void updateUsersWithExistsUserNameWithAdmin_fail() throws Exception {
        UserEntity userAlreadyAddDb = userEntityGenerate("kosto","test@test.ru", true);

        UserEntity updateUser = userEntityGenerate("username","test1@test.ru", true);
        updateUser.setUsername(userAlreadyAddDb.getUsername());

        sendPutAndGetResultActions("/api/users", updateUser).andExpect(status().is4xxClientError());

        assert userEntityRepository.existsByUsername(updateUser.getUsername());
    }

    @Test
    @Description("Обновление user на существующиющий email")
    public void updateUsersWithExistsEmailWithAdmin_fail() throws Exception {
        UserEntity userAlreadyAddDb = userEntityGenerate("kosto","test@test.ru", true);

        UserEntity updateUser = userEntityGenerate("username","test1@test.ru", true);
        updateUser.setEmail(userAlreadyAddDb.getEmail());

        sendPutAndGetResultActions("/api/users", updateUser).andExpect(status().is4xxClientError());

        assert userEntityRepository.existsByUsername(updateUser.getUsername());
    }

    @Test
    @Description("Добавление 2 одинаковых user с одинаковым username")
    public void addUsersExistsUserNameWithAdmin_fail() throws Exception {
        userEntityGenerate("kosto","test@test.ru", true);
        UserEntity user = userEntityGenerate("username", "test1@test.ru", false);

        sendPostAndGetResultActions("/api/users", user).andExpect(status().isOk());
        sendPostAndGetResultActions("/api/users", user).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Добавление 2 одинаковых user с одинаковым email")
    public void addUsersExistsEmailWithAdmin_fail() throws Exception {
        userEntityGenerate("kosto","test@test.ru", true);
        UserEntity user = userEntityGenerate("username", "test@test.ru", false);

        sendPostAndGetResultActions("/api/users", user).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Получение списка пользователей")
    public void getUsersWithAdmin_ok() throws Exception {
        UserEntity addUser1 = userEntityGenerate("kosto","test@test.ru",true);
        UserEntity addUser2 = userEntityGenerate("user1","test1@test.ru",true);

        getActionResult("/api/users"
        ).andExpect(jsonPath("$[0].idUser").value(addUser1.getIdUser()))
        .andExpect(jsonPath("$[1].idUser").value(addUser2.getIdUser()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Получение пользователя по id")
    public void getUsersIdUserWithAdmin_ok() throws Exception {
        UserEntity user = userEntityGenerate("kosto","test@test.ru",true);

        getActionResult("/api/users/{idUser}", user.getIdUser()
        ).andExpect(jsonPath("$.name").value(user.getName()))
        .andExpect(jsonPath("$.lastName").value(user.getLastName()))
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
        .andExpect(status().isOk());

        assert userEntityRepository.existsById(user.getIdUser());
    }

    @Test
    @Description("Получение пользователя по id не существующего пользователя")
    public void getUsersIdUserWithAdmin_fail() throws Exception {
        userEntityGenerate("kosto","test@test.ru",true);

        getActionResult("/api/users/{idUser}", 0).andExpect(status().is4xxClientError());
    }

    @Test
    @Description("Получение пользователя по username")
    public void getUsersUserNameWithAdmin_ok() throws Exception {
        UserEntity user = userEntityGenerate("kosto","test@test.ru",true);

        getActionResult("/api/users/userName/{userName}", user.getUsername()
        ).andExpect(jsonPath("$.idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Получение пользователя по имени и фамилии")
    public void getUsersNameLastNameWithAdmin_ok() throws Exception {
        UserEntity user = userEntityGenerate("kosto","test@test.ru",true);

        getActionResult("/api/users?name={name}&lastName={lastName}", user.getName(), user.getLastName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$[0].name").value(user.getName()))
        .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Получение пользователя по имени")
    public void getUsersNameWithAdmin_ok() throws Exception {
        UserEntity user = userEntityGenerate("kosto","test@test.ru",true);

        getActionResult("/api/users?name={name}", user.getName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$[0].name").value(user.getName()))
        .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Получение пользователя по фамилии")
    public void getUsersLastNameWithAdmin_ok() throws Exception {
        UserEntity user = userEntityGenerate("kosto","test@test.ru",true);

        getActionResult("/api/users?lastName={lastName}", user.getLastName()
        ).andExpect(jsonPath("$[0].idUser").value(user.getIdUser()))
        .andExpect(jsonPath("$[0].name").value(user.getName()))
        .andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
        .andExpect(status().isOk());
    }

    @Test
    @Description("Блокировка пользователя")
    public void patchBlockUserWithAdmin_ok() throws Exception {
        userEntityGenerate("kosto","test@test.ru",true);
        UserEntity user =  userEntityGenerate("test","block@test.ru",true);

        sendPatchAndGetResultActions(
             "/api/users/block?idUser={idUser}&isBlock={isBlock}", user.getIdUser(), false)
                .andExpect(jsonPath("$.isAccountNonBlock").value(false))
                .andExpect(jsonPath("$.idUser").value(user.getIdUser()))
                .andExpect(status().isOk());
    }

    @Test
    @Description("Блокировка пользователя без параметра isBlock")
    public void patchBlockUserWithAdmin_fail() throws Exception {
        UserEntity user = userEntityGenerate("kosto", "test@test.ru",true);

        sendPatchAndGetResultActions("/api/users/block?idUser={idUser}", user.getIdUser()
        ) .andExpect(status().is4xxClientError());

        assert userEntityRepository.existsByUsername(user.getUsername());
    }

    @Test
    @Description("удаление добавленного пользователя")
    public void deleteUsersWithAdmin_ok() throws Exception {
        UserEntity user = userEntityGenerate("kosto","test@test.ru",true);

        deleteById("/api/users/{idUser}", user.getIdUser()).andExpect(status().is2xxSuccessful());

        assert !userEntityRepository.existsById(user.getIdUser());
    }

    @Test
    @Description("удаление несуществующего пользователя")
    public void deleteUsersWithAdmin_fail() throws Exception {
        userEntityGenerate("kosto","test@test.ru",true);

        deleteById("/api/users/{idUser}", 0L).andExpect(status().is4xxClientError());
    }
}
