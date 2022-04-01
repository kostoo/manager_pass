package com.managerPass;

import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.response.JwtResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.*;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void postTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName("MEDIUM");

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/authUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();




        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/task")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());
    }

    @Test
    public void updateTask_success(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName("MEDIUM");

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                                                                   LocalDateTime.now().getMonth(),
                                                                   LocalDateTime.now().getDayOfMonth()+1),
                                                   LocalTime.now()));
        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                           .uri("/api/authUser")
                                           .bodyValue(loginRequest)
                                           .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> addEntity = client.post()
                                                     .uri("/api/task")
                                                     .bodyValue(taskEntity)
                                                     .retrieve()
                                                     .toEntity(TaskEntity.class).block();

        Objects.requireNonNull(addEntity).getBody().setName(RandomStringUtils.random(10, true, false));

        ResponseEntity<TaskEntity> updateEntity = client.put()
                                                        .uri("/api/task")
                                                        .bodyValue(addEntity.getBody())
                                                        .retrieve()
                                                        .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(updateEntity).getBody().getName().equals(addEntity.getBody().getName());
    }

    @Test
    public void deleteTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName("MEDIUM");

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/authUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/task")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        ResponseEntity<Void> deleteEntity = client.delete()
                                                  .uri("/api/task/"+ responseEntity.getBody().getIdTask())
                                                  .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(deleteEntity).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getTask_success(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName("MEDIUM");

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .defaultHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON))
                                    .build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/authUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/task")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        ResponseEntity<TaskEntity> getEntity = client.get()
                                                     .uri("/api/task/" + responseEntity.getBody().getIdTask())
                                                     .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                                                     .retrieve()
                                                     .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(getEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(getEntity.getBody()).getName().equals(taskEntity.getName());
    }

    @Test
    public void getTaskGetAll(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName("MEDIUM");

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/authUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/task")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/tasks")
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        List<TaskEntity> taskEntities = getAllEntity.block();

        assert taskEntities.size() > 0;
        assert taskEntities.stream()
                           .filter(task -> task.getName().equals(responseEntity.getBody().getName())).count() >= 1;
    }

    @Test
    public void getTasksByUserGetAll(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName("MEDIUM");

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                   LocalTime.now()));

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/authUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/task")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/tasks/byUser")
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        List<TaskEntity> taskEntities = getAllEntity.block();

        assert taskEntities.size() > 0;
        assert taskEntities.stream()
                           .filter(task -> task.getName().equals(responseEntity.getBody().getName())).count() >= 1;

    }

    @Test
    public void getTasksByUserByPriorityIdGetAll(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(RandomStringUtils.random(10, true, false));
        PriorityEntity priority = new PriorityEntity();
        priority.setName("MEDIUM");

        taskEntity.setPriority(priority);
        taskEntity.setDateTimeStart(LocalDateTime.now());
        taskEntity.setDateTimeFinish(LocalDateTime.of(LocalDate.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth()+1),
                LocalTime.now()));

        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:" + randomServerPort)
                .build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                .uri("/api/authUser")
                .bodyValue(loginRequest)
                .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                .baseUrl("http://localhost:" + randomServerPort)
                .defaultHeader("Authorization","Bearer " + jwtToken)
                .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                .uri("/api/task")
                .bodyValue(taskEntity)
                .retrieve()
                .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                .uri("/api/tasks//tasks/byUser/byPriority/1")
                .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        List<TaskEntity> taskEntities = getAllEntity.block();

        assert taskEntities.size() > 0;
        assert taskEntities.stream()
                .filter(task -> task.getName().equals(responseEntity.getBody().getName())).count() >= 1;

    }
}
