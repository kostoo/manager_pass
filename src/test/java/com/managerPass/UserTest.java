package com.managerPass;

import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.response.JwtResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @LocalServerPort
    int randomServerPort;

    String randomString = RandomStringUtils.random(10, true, false);


    @Test
    public void postEmployee_success() {
        UserEntity employee = UserEntity.builder()
                                        .name(randomString)
                                        .lastName(randomString)
                                        .username(randomString)
                                        .build();

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

        ResponseEntity<UserEntity> responseEntity = client.post()
                                                          .uri("/api/addUser")
                                                          .bodyValue(employee)
                                                          .retrieve()
                                                          .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert employee.getName().equals(Objects.requireNonNull(responseEntity.getBody()).getName());
        //  ResponseEntity<Void> responseEntity = responseSpec.toBodilessEntity().block();
        //Verify request succeed
      //  assert responseEntity != null;
    }

    @Test
    public void getEmployeeByName_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(randomString)
                                        .lastName(randomString)
                                        .username(randomString)
                                        .build();


        LoginRequest loginRequest = new LoginRequest("kosto","password");

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/postAuthUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<?> responseEntity = client.get()
                                                 .uri("/api/getUserByName/"+ employee.getName())
                                                 .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getEmployeeByUserName_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(randomString)
                                        .lastName(randomString)
                                        .username(randomString)
                                        .build();


        LoginRequest loginRequest = new LoginRequest("kosto","password");

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/postAuthUser")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/addUser")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(addUserResponseEntity).getStatusCode().is2xxSuccessful();

        ResponseEntity<UserEntity> responseEntity = client.get()
                                                          .uri("/api/getUserByUserName/"+ employee.getUsername())
                                                          .retrieve()
                                                          .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert Objects.equals(responseEntity.getBody().getUsername(), addUserResponseEntity.getBody().getUsername());
    }

    @Test
    public void deleteEmployeeById_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(randomString)
                                        .lastName(randomString)
                                        .username(randomString)
                                        .build();

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

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/addUser")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert addUserResponseEntity != null;
        ResponseEntity<?> deleteUserResponseEntity = client.delete()
                                                           .uri("api/deleteUser/"+
                                                                 Objects.requireNonNull(
                                                                            addUserResponseEntity.getBody())
                                                                                                 .getIdUser()
                                                            )
                                                           .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(deleteUserResponseEntity).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void putEmployee_success(){
        UserEntity employee = UserEntity.builder()
                                        .name(randomString)
                                        .lastName(randomString)
                                        .username(randomString)
                                        .build();


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

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/addUser")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert addUserResponseEntity != null;
        Objects.requireNonNull(addUserResponseEntity.getBody())
                                                    .setUsername(randomString);

        ResponseEntity<UserEntity> updateUserResponseEntity = client.put()
                                                                    .uri("api/updateUser/")
                                                                    .bodyValue(addUserResponseEntity.getBody())
                                                                    .retrieve().toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(updateUserResponseEntity).getStatusCode().is2xxSuccessful();
    }

}
