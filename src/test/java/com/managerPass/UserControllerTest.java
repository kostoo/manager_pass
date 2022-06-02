package com.managerPass;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.repository.RoleRepository;
import com.managerPass.repository.TaskEntityRepository;
import com.managerPass.repository.UserEntityRepository;
import com.managerPass.service.TaskEntityService;
import com.managerPass.service.UserEntityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TaskEntityRepository taskEntityRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    TaskEntityService taskEntityService;

    public UserEntity userEntity;
    public RoleEntity roleEntity;
    public UserRequest userRequest;

    @BeforeEach
    public void prepareDataBeforeTest() {
        roleEntity = new RoleEntity();
        roleEntity.setName(ERole.ROLE_ADMIN);

        roleEntity = roleRepository.save(roleEntity);

        userEntity = UserEntity.builder()
                               .username("kosto")
                               .email("test@gmail.com")
                               .roles(Set.of(roleEntity))
                               .isAccountNonBlock(true)
                               .isAccountActive(true)
                               .build();

        userEntity = userEntityRepository.save(userEntity);

        userRequest = UserRequest.builder()
                .name("nikita")
                .username("kostos")
                .lastName("nesterov")
                .roles(Set.of(roleEntity))
                .email("test1@gmail.com")
                .build();

    }

    @AfterEach
    public void deleteAll() {
        taskEntityRepository.deleteAllInBatch();
        userEntityRepository.deleteAllInBatch();
        roleRepository.deleteAllInBatch();
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void addUsersWithAdmin_ok() throws Exception {
        mvc.perform(post("/api/users")
           .contentType(MediaType.APPLICATION_JSON)
           .content((objectMapper.writeValueAsBytes(userRequest))))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void addUsersWithUser_fail() throws Exception {
        mvc.perform(post("/api/users")
           .contentType(MediaType.APPLICATION_JSON)
           .content((objectMapper.writeValueAsBytes(userRequest)))).andDo(print())
           .andExpect(status().is4xxClientError());
    }

    @Test
    public void addUsersWithUnAuthorized_ok() throws Exception {
        mvc.perform(post("/api/users")
           .contentType(MediaType.APPLICATION_JSON)
           .content((objectMapper.writeValueAsBytes(userRequest))))
           .andDo(print())
           .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getUsersWithAdmin_ok() throws Exception {
        mvc.perform(get("/api/users")
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void getUsersWithUser_fail() throws Exception {
        mvc.perform(get("/api/users")
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getUsersIdUserWithAdmin_ok() throws Exception {
        mvc.perform(get("/api/users/{idUser}", userEntity.getIdUser())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void getUsersIdUserWithUser_fail() throws Exception {
        mvc.perform(get("/api/users/{idUser}", userEntity.getIdUser())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void deleteUsersIdUserWithUser_ok() throws Exception {
        mvc.perform(get("/api/users/{idUser}", userEntity.getIdUser())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status()
           .is4xxClientError());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void deleteUsersIdUserWithAdmin_fail() throws Exception {
        mvc.perform(get("/api/users/{idUser}", userEntity.getIdUser())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getUsersNameLastNameWithAdmin_ok() throws Exception {
        mvc.perform(get("/api/users?name={name}&lastName={lastName}",
                        userEntity.getName(), userEntity.getLastName()
           ).contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getUsersNameLastNameWithUser_ok() throws Exception {
        mvc.perform(get("/api/users?name={name}&lastName={lastName}",
                        userEntity.getName(), userEntity.getLastName()
                ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getUsersNameWithUser_ok() throws Exception {
        mvc.perform(get("/api/users?name={name}", userEntity.getName()
           ).contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getUsersLastNameWithUser_ok() throws Exception {
        mvc.perform(get("/api/users?lastName={lastName}", userEntity.getName()
           ).contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getUsersUserNameWithAdmin_ok() throws Exception {
        mvc.perform(get("/api/users/userName/{userName}", userEntity.getUsername())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void getUsersUserNameWithUser_fail() throws Exception {
        mvc.perform(get("/api/users/userName/{userName}", userEntity.getUsername())
           .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().is4xxClientError());
    }
}
