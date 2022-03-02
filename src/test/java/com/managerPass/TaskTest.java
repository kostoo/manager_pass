package com.managerPass;

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

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskTest {

    @LocalServerPort
    int randomServerPort;

    String randomString = RandomStringUtils.random(10, true, false);

    @Test
    public void postTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(randomString);

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/postAuthUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/addTask")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());
    }

    @Test
    public void updateTask_success(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(randomString);

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                           .uri("/api/postAuthUser")
                                           .bodyValue(loginRequest)
                                           .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> addEntity = client.post()
                                                     .uri("/api/addTask")
                                                     .bodyValue(taskEntity)
                                                     .retrieve()
                                                     .toEntity(TaskEntity.class).block();

        Objects.requireNonNull(addEntity).getBody().setName(randomString);

        ResponseEntity<TaskEntity> updateEntity = client.put()
                                                        .uri("/api/updateTask")
                                                        .bodyValue(addEntity.getBody())
                                                        .retrieve()
                                                        .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(updateEntity).getBody().getName().equals(addEntity.getBody().getName());
    }

    @Test
    public void deleteTask_success() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(randomString);

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/postAuthUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/addTask")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        ResponseEntity<Void> deleteEntity = client.delete()
                                                  .uri("/api/deleteTask/"+ responseEntity.getBody().getIdTask())
                                                  .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(deleteEntity).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getTask_success(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(randomString);

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .defaultHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON))
                                    .build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/postAuthUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/addTask")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        ResponseEntity<TaskEntity> getEntity = client.get()
                                                     .uri("/api/getTask/"+ responseEntity.getBody().getIdTask())
                                                     .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                                                     .retrieve()
                                                     .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(getEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(getEntity.getBody()).getName().equals(taskEntity.getName());
    }

    @Test
    public void getUserGetAll(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(randomString);

        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        LoginRequest loginRequest = new LoginRequest("kosto","password");

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/postAuthUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<TaskEntity> responseEntity = client.post()
                                                          .uri("/api/addTask")
                                                          .bodyValue(taskEntity)
                                                          .retrieve()
                                                          .toEntity(TaskEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(responseEntity.getBody()).getName().equals(taskEntity.getName());

        Mono<List<TaskEntity>> getAllEntity = client.get()
                                                    .uri("/api/getAllTask")
                                                    .retrieve().bodyToMono(new ParameterizedTypeReference<>() {});

        assert Objects.requireNonNull(getAllEntity.block()).isEmpty();

    }
}
