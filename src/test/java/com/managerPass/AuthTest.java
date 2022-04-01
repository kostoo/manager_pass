package com.managerPass;

import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void postAuthUser(){

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
    }

}
