package com.managerPass;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.JwtResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskTest {

    @LocalServerPort
    int randomServerPort;

    @BeforeAll
    public void beforeTest() {
        try {

            WebClient client = WebClient.builder()
                    .baseUrl("http://localhost:" + randomServerPort)
                    .build();

            SignupRequest signupRequest = new SignupRequest();
            signupRequest.setUsername("nesterov");
            signupRequest.setRole(Set.of("user", "admin"));
            signupRequest.setEmail("nesterov1@gmail.com");
            signupRequest.setPassword("password");


            WebClient.ResponseSpec responseSpec = client.post()
                    .uri("/api/register")
                    .bodyValue(signupRequest)
                    .retrieve();

            Objects.requireNonNull(responseSpec.toBodilessEntity().block()).getStatusCode();

            Thread.sleep(200);
            WebClient.ResponseSpec responseActivate = client.post()
                    .uri("api/register/activate/" + signupRequest.getUsername()).retrieve();
            responseActivate.toBodilessEntity().block();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void postTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now())
        );

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                .uri("/api/auth")
                .bodyValue(loginRequest)
                .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                .baseUrl("http://localhost:" + randomServerPort)
                .defaultHeader("Authorization","Bearer " + jwtToken)
                .build();


         ResponseEntity<TaskEntity> responseEntity = client.post()
                                                           .uri("/api/tasks")
                                                           .bodyValue(taskEntity)
                                                           .retrieve()
                                                           .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());
    }

    @Test
    public void updateTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now())
        );

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> addEntity = client.post()
                                                     .uri("/api/tasks")
                                                     .bodyValue(taskEntity)
                                                     .retrieve()
                                                     .toEntity(TaskEntity.class).block();

        Objects.requireNonNull(Objects.requireNonNull(addEntity).getBody()).setName(
                RandomStringUtils.random(10, true, false)
        );

        ResponseEntity<TaskEntity> updateEntity = client.put()
                                                        .uri("/api/tasks")
                                                        .bodyValue(Objects.requireNonNull(addEntity.getBody()))
                                                        .retrieve()
                                                        .toEntity(TaskEntity.class).block();

        assert Objects.equals(Objects.requireNonNull(Objects.requireNonNull(updateEntity).getBody()).getName(), addEntity.getBody().getName());
    }

    @Test
    public void deleteTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth()+1),
                                     LocalTime.now())
        );

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/tasks")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        ResponseEntity<Void> deleteEntity = client.delete()
                                                  .uri("/api/tasks/"+ responseEntity.getBody().getIdTask())
                                                  .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(deleteEntity).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now())
        );

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .defaultHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON))
                                    .build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/tasks")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        ResponseEntity<TaskEntity> getEntity = client.get()
                                                     .uri("/api/tasks/" + responseEntity.getBody().getIdTask())
                                                     .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                                                     .retrieve()
                                                     .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(getEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(getEntity.getBody()).getName().equals(taskEntity.getName());
    }

    @Test
    public void getTaskGetAll() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/tasks")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/tasks")
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        List<TaskEntity> taskEntities = getAllEntity.block();

        assert Objects.requireNonNull(taskEntities).size() > 0;
        assert taskEntities.stream().anyMatch(task -> task.getName().equals(responseEntity.getBody().getName()));
    }

    @Test
    public void getTasksByUserGetAll() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/tasks")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/tasks/user")
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        List<TaskEntity> taskEntities = getAllEntity.block();

        assert Objects.requireNonNull(taskEntities).size() > 0;
        assert taskEntities.stream().anyMatch(task -> task.getName().equals(responseEntity.getBody().getName()));
    }

    @Test
    public void getTasksByUserGetAllByPage() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/tasks")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/tasks/user/0")
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        List<TaskEntity> taskEntities = getAllEntity.block();


        assert Objects.requireNonNull(taskEntities).size() > 0;

    }
    @Test
    public void getTasksByUserByPriorityIdGetAll() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/tasks")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/tasks/user/priority/2")
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        List<TaskEntity> taskEntities = getAllEntity.block();

        assert Objects.requireNonNull(taskEntities).size() > 0;
        assert taskEntities.stream().anyMatch(task -> task.getName().equals(responseEntity.getBody().getName()));
    }

    @Test
    public void getAllByUserIdAndDateAfterBefore() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        taskEntity.setMessage(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(
              LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                                            LocalDateTime.now().getMonth(),
                                 LocalDateTime.now().getDayOfMonth()+1),
                                            LocalTime.now())
        );

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/tasks")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        taskEntity.setDateTimeStart( taskEntity.getDateTimeStart().minusDays(2));
        taskEntity.setDateTimeFinish(taskEntity.getDateTimeFinish().plusDays(2));
        taskEntity.setDateTimeFinish(taskEntity.getDateTimeFinish().plusHours(1));

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/tasks/user/0/"+ taskEntity.getDateTimeStart()
                                                            +"/"+ taskEntity.getDateTimeFinish()
                                                    )
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});

        List<TaskEntity> taskEntities = getAllEntity.block();
        assert taskEntities != null;

        for (TaskEntity task: taskEntities) {
            System.out.println(task);
            assert task.getDateTimeStart().isAfter(taskEntity.getDateTimeStart());
            assert task.getDateTimeFinish().isBefore(taskEntity.getDateTimeFinish());
        }
    }
}
