package com.managerPass;

import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.response.JwtResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void postUser_success() {
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .build();

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

        ResponseEntity<UserEntity> responseEntity = client.post()
                                                          .uri("/api/user")
                                                          .bodyValue(employee)
                                                          .retrieve()
                                                          .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert employee.getName().equals(Objects.requireNonNull(responseEntity.getBody()).getName());
    }

    @Test
    public void getUserByName_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .build();


        LoginRequest loginRequest = new LoginRequest("kosto","password");

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/authUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<?> responseUserByName = client.get()
                                                 .uri("/api/user/lastName/"+ employee.getLastName())
                                                 .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(responseUserByName).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getUserByUserName_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .build();


        LoginRequest loginRequest = new LoginRequest("kosto","password");

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/authUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/user")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(addUserResponseEntity).getStatusCode().is2xxSuccessful();

        ResponseEntity<UserEntity> responseEntity = client.get()
                                                          .uri("/api/user/userName/"+ employee.getUsername())
                                                          .retrieve()
                                                          .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.equals(responseEntity.getBody().getUsername(), addUserResponseEntity.getBody().getUsername());
    }

    @Test
    public void deleteUserById_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .build();

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

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/user")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert addUserResponseEntity != null;
        ResponseEntity<UserEntity> deleteUserResponseEntity = client.delete()
                                                                    .uri("/api/user/"+ Objects.requireNonNull(
                                                                        addUserResponseEntity.getBody()).getIdUser())
                                                                    .retrieve().toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(deleteUserResponseEntity).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void putUser_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .build();


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

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/user")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert addUserResponseEntity != null;
        Objects.requireNonNull(addUserResponseEntity.getBody())
                                                    .setUsername(RandomStringUtils.random(10, true, false));

        ResponseEntity<UserEntity> updateUserResponseEntity = client.put()
                                                                    .uri("api/user/")
                                                                    .bodyValue(addUserResponseEntity.getBody())
                                                                    .retrieve().toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(updateUserResponseEntity).getStatusCode().is2xxSuccessful();
    }

}
