package com.managerPass;

import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.JwtResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {

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

            WebClient.ResponseSpec responseActivate = client.post().uri(
                                                             "api/register/activate/" + signupRequest.getUsername()
                                                            ).retrieve();
            responseActivate.toBodilessEntity().block();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void postUser_success() {
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .isAccountActive(false)
                                        .isAccountNonBlock(false)
                                        .build();

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

        ResponseEntity<UserEntity> responseEntity = client.post()
                                                          .uri("/api/users")
                                                          .bodyValue(employee)
                                                          .retrieve()
                                                          .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();
        assert employee.getName().equals(Objects.requireNonNull(responseEntity.getBody()).getName());
    }

    @Test
    public void getUserLastName_success() {
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .isAccountActive(false)
                                        .isAccountNonBlock(false)
                                        .build();


        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<?> responseUserByName = client.get()
                                                     .uri("/api/users/lastName/" + employee.getLastName())
                                                     .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(responseUserByName).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void getUserUserName_success() {
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .isAccountActive(false)
                                        .isAccountNonBlock(false)
                                        .build();

        LoginRequest loginRequest = new LoginRequest("nesterov","password");

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + randomServerPort).build();

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        client = WebClient.builder()
                          .baseUrl("http://localhost:" + randomServerPort)
                          .defaultHeader("Authorization","Bearer " + jwtToken)
                          .build();

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/users")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(addUserResponseEntity).getStatusCode().is2xxSuccessful();

        ResponseEntity<UserEntity> responseEntity = client.get()
                                                          .uri("/api/users/userName/" + employee.getUsername())
                                                          .retrieve()
                                                          .toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(responseEntity).getStatusCode().is2xxSuccessful();

        assert Objects.equals(Objects.requireNonNull(responseEntity.getBody()).getUsername(),
                              Objects.requireNonNull(addUserResponseEntity.getBody()).getUsername()
        );
    }

    @Test
    public void deleteUserById_success() {
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .isAccountActive(false)
                                        .isAccountNonBlock(false)
                                        .build();

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

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/users")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert addUserResponseEntity != null;
        ResponseEntity<UserEntity> deleteUserResponseEntity = client.delete()
                                                                    .uri("/api/users/" + Objects.requireNonNull(
                                                                        addUserResponseEntity.getBody()).getIdUser()
                                                                    ).retrieve().toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(deleteUserResponseEntity).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void putUser_success() {
        UserEntity employee = UserEntity.builder()
                                        .name(RandomStringUtils.random(10, true, false))
                                        .lastName(RandomStringUtils.random(10, true, false))
                                        .username(RandomStringUtils.random(10, true, false))
                                        .isAccountActive(false)
                                        .isAccountNonBlock(false)
                                        .build();


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

        ResponseEntity<UserEntity> addUserResponseEntity = client.post()
                                                                 .uri("/api/users")
                                                                 .bodyValue(employee)
                                                                 .retrieve()
                                                                 .toEntity(UserEntity.class).block();

        assert addUserResponseEntity != null;
        Objects.requireNonNull(addUserResponseEntity.getBody())
                                                    .setUsername(RandomStringUtils.random(
                                                            10, true, false)
                                                    );

        ResponseEntity<UserEntity> updateUserResponseEntity = client.put()
                                                                    .uri("api/users")
                                                                    .bodyValue(addUserResponseEntity.getBody())
                                                                    .retrieve().toEntity(UserEntity.class).block();

        assert Objects.requireNonNull(updateUserResponseEntity).getStatusCode().is2xxSuccessful();
    }
}
