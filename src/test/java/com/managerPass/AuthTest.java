package com.managerPass;

import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.payload.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Set;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void registration(){
        WebClient client = WebClient.builder()
                                    .baseUrl("http://localhost:" + randomServerPort)
                                    .build();

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("nesterov");
        signupRequest.setRole(Set.of("USER","ADMIN"));
        signupRequest.setEmail("neesterov1@gmail.com");
        signupRequest.setPassword("password");

        WebClient.ResponseSpec responseSpec = client.post()
                                                    .uri("/api/register")
                                                    .bodyValue(signupRequest)
                                                    .retrieve();

        assert Objects.requireNonNull(responseSpec.toBodilessEntity().block()).getStatusCode().is2xxSuccessful();

        ResponseEntity<Void> responseActivate = client.post()
                                                      .uri("/api/register/activate/"+ signupRequest.getUsername())
                                                      .retrieve().toBodilessEntity().block();

        assert Objects.requireNonNull(responseActivate).getStatusCode().is2xxSuccessful();

        LoginRequest loginRequest = new LoginRequest(signupRequest.getUsername(), signupRequest.getPassword());

        JwtResponse userJwtResponse = client.post()
                                            .uri("/api/auth")
                                            .bodyValue(loginRequest)
                                            .retrieve().bodyToMono(JwtResponse.class).block();

        assert userJwtResponse!= null;

        String jwtToken = Objects.requireNonNull(userJwtResponse).getToken();

        System.out.println(jwtToken);
    }
}
