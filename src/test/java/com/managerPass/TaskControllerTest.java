package com.managerPass;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.repository.PriorityEntityRepository;
import com.managerPass.repository.RoleRepository;
import com.managerPass.repository.TaskEntityRepository;
import com.managerPass.repository.UserEntityRepository;
import com.managerPass.service.TaskEntityService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    PriorityEntityRepository priorityEntityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TaskEntityRepository taskEntityRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    TaskEntityService taskEntityService;

    public TaskEntity taskEntity;
    public RoleEntity roleEntity;
    public PriorityEntity priority;
    TaskRequest taskRequest;

    public static LocalDateTime localDateTimeFinishPlus1Month = LocalDateTime.of(LocalDate.of(
         LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth()+1
    ), LocalTime.now());

    @BeforeEach
    public void prepareDataBeforeTest() {

        priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        priority = priorityEntityRepository.save(priority);

        roleEntity = new RoleEntity();
        roleEntity.setName(ERole.ROLE_ADMIN);

        roleEntity = roleRepository.save(roleEntity);

        UserEntity userEntity = UserEntity.builder()
                                          .username("kosto")
                                          .email("test@gmail.com")
                                          .roles(Set.of(roleEntity))
                                          .isAccountNonBlock(true)
                                          .isAccountActive(true)
                                          .build();

        userEntity = userEntityRepository.save(userEntity);

        taskEntity = TaskEntity.builder()
                               .name("testtask")
                               .message("message")
                               .dateTimeStart(LocalDateTime.now())
                               .dateTimeFinish(localDateTimeFinishPlus1Month)
                               .priority(priority)
                               .userEntity(userEntity)
                               .build();

        taskEntity = taskEntityRepository.save(taskEntity);

        taskRequest = TaskRequest.builder()
                                 .name("add test")
                                 .message("message test")
                                 .priority(priority)
                                 .dateTimeStart(LocalDateTime.now())
                                 .dateTimeFinish(localDateTimeFinishPlus1Month)
                                 .build();

    }

    @AfterEach
    public void deleteAll() {
    taskEntityRepository.deleteAllInBatch();
    userEntityRepository.deleteAllInBatch();
    roleRepository.deleteAllInBatch();
    priorityEntityRepository.deleteAllInBatch();
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getTasksWithAdmin_ok() throws Exception {
        mvc.perform(get("/api/tasks")
           .contentType(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kostos" , roles = "USER")
    public void getTasksWithUser_fail() throws Exception {
        mvc.perform(get("/api/tasks")
           .contentType(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status()
           .is4xxClientError());
    }

    @Test
    public void getTasksWithUnAuthorized_fail() throws Exception {
        mvc.perform(get("/api/tasks")
           .contentType(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status()
           .isUnauthorized());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getTasksNameWithAdmin_ok() throws Exception {
        mvc.perform(get("/api/tasks/name/{name}", taskEntity.getName())
           .accept(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status()
           .is2xxSuccessful());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void getTasksNameWithUser_fail() throws Exception {
        mvc.perform(get("/api/tasks/name/{name}", taskEntity.getName())
           .accept(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status().is4xxClientError());
    }

    @Test
    public void getTasksNameWithUnAuthorized_fail() throws Exception {
        mvc.perform(get("/api/tasks/name/{name}", taskEntity.getName())
           .accept(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "kostos" , roles = "ADMIN")
    public void getTasksIdTaskWithAdmin_ok() throws Exception {
        mvc.perform(get("/api/tasks/{idTask}", taskEntity.getIdTask())
           .accept(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.name").value(taskEntity.getName()))
           .andExpect(jsonPath("$.message").value(taskEntity.getMessage()))
           .andDo(print())
           .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getTasksUsersAdmin_ok() throws Exception {
        mvc.perform(get("/api/tasks/users")
           .accept(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "kostos" , roles = "USER")
    public void getTasksIdTaskWithUser_ok() throws Exception {
        mvc.perform(get("/api/tasks/{idTask}", taskEntity.getIdTask())
           .accept(MediaType.APPLICATION_JSON)
           ).andExpect(jsonPath("$.name").value(taskEntity.getName()))
           .andExpect(jsonPath("$.message").value(taskEntity.getMessage()))
           .andDo(print())
           .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getTasksIdTaskWithUnAuthorized_fail() throws Exception {
        mvc.perform(get("/api/tasks/{idTask}", taskEntity.getIdTask())
           .accept(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "kostos" , roles = "ADMIN")
    public void deleteTasksWithAdmin_ok() throws Exception {
        assert taskEntity.getIdTask() != null;
        Long idTask = taskEntity.getIdTask();

        mvc.perform(delete("/api/tasks/{idTask}", idTask ))
           .andDo(print())
           .andExpect(status().is2xxSuccessful());

        assert !taskEntityRepository.existsById(idTask);
    }

    @Test
    @WithMockUser(username = "kostos" , roles = "USER")
    public void deleteTasksWithUser_ok() throws Exception {
        assert taskEntity.getIdTask() != null;
        Long idTask = taskEntity.getIdTask();

        mvc.perform(delete("/api/tasks/{idTask}", idTask ))
           .andDo(print())
           .andExpect(status().is2xxSuccessful());

        assert !taskEntityRepository.existsById(idTask);
    }

    @Test
    public void deleteTasksWithUnAuthorized_fail() throws Exception {
        assert taskEntity.getIdTask() != null;
        Long idTask = taskEntity.getIdTask();

        mvc.perform(delete("/api/tasks/{idTask}", idTask)
           .contentType(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status().isUnauthorized());

        assert !taskEntityRepository.existsById(idTask);
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void addTasksWithAdmin_ok() throws Exception {
        mvc.perform(post("/api/tasks", taskEntity)
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsBytes(taskRequest))
           ).andDo(print()).andExpect(status().is2xxSuccessful())
           .andExpect(jsonPath("$.name").value(taskRequest.getName()))
           .andExpect(jsonPath("$.message").value(taskRequest.getMessage()));
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void addTasksWithUser_ok() throws Exception {
        mvc.perform(post("/api/tasks")
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsBytes(taskRequest))
           ).andDo(print()).andExpect(status().is2xxSuccessful())
           .andExpect(jsonPath("$.name").value(taskRequest.getName()))
           .andExpect(jsonPath("$.message").value(taskRequest.getMessage()));
    }

    @Test
    public void addTasksWithUnAuthorized_fail() throws Exception {
        mvc.perform(post("/api/tasks")
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsBytes(taskRequest))
           ).andDo(print())
           .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void updateTasksWithAdmin_ok() throws Exception {
        assert taskEntity != null;
        assert taskEntity.getIdTask() != null;

        taskEntity.setName("update name");
        taskEntity.setMessage("update message");

        mvc.perform(put("/api/tasks")
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsBytes(taskEntity))
           ).andDo(print())
           .andExpect(status().is2xxSuccessful())
           .andExpect(jsonPath("$.name").value("update name"))
           .andExpect(jsonPath("$.message").value("update message"));
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void updateTasksWithUser_ok() throws Exception {
        assert taskEntity != null;
        assert taskEntity.getIdTask() != null;

        taskEntity.setName("update name");
        taskEntity.setMessage("update message");

        mvc.perform(put("/api/users")
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsBytes(taskEntity))
           ).andDo(print())
           .andExpect(status().is2xxSuccessful())
           .andExpect(jsonPath("$.name").value("update name"))
           .andExpect(jsonPath("$.message").value("update message"));
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getTasksUsersWithIdPriorityAdmin_ok() throws Exception {
        assert priority != null;

        mvc.perform(get("/api/tasks/users?idPriority={idPriority}", priority.getId())
           ).andDo(print())
           .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getTasksUsersWithIdPriorityFinishAdmin_ok() throws Exception {
        mvc.perform(get("/api/tasks/users?idPriority={idPriority}", priority.getId())
           .contentType(MediaType.APPLICATION_JSON)
           ).andDo(print())
           .andExpect(status().is2xxSuccessful());
    }



    @Test
    public void getTasksUsersWithIdPriorityUnAuthorized_fail() throws Exception {
        mvc.perform(get("/api/tasks/users?idPriority={idPriority}", priority.getId())
           .contentType(MediaType.APPLICATION_JSON_VALUE)
           ).andDo(print())
           .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "ADMIN")
    public void getTasksUsersWithDateStartFinishAdmin_ok() throws Exception {
        mvc.perform(get("/api/tasks/users?startDateTime={startdateTime}&dateTimeFinish={dateTimeFinish}",
                        taskEntity.getDateTimeStart(), taskEntity.getDateTimeFinish())
           ).andDo(print())
           .andExpect(status()
           .is2xxSuccessful());
    }

    @Test
    @WithMockUser( username = "kosto" , roles = "USER")
    public void getTasksUsersWithDateStartFinishUser_ok() throws Exception {
    mvc.perform(get("/api/tasks/users?startDateTime={startdateTime}&dateTimeFinish={dateTimeFinish}",
                                taskEntity.getDateTimeStart(), taskEntity.getDateTimeFinish())
       ).andDo(print())
       .andExpect(status()
       .is2xxSuccessful());
    }
}
