package com.managerPass.controller;

import com.managerPass.payload.request.LoginRequest;
import com.managerPass.payload.request.SignupRequest;
import com.managerPass.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/auth")
    public ResponseEntity<?> postAuth(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping(path = "/register",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postRegister(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping(path = "/register/activate/{userName}")
    public ResponseEntity<?> postRegisterActivate(@PathVariable (value = "userName") String userName) {
        return authService.activateUser(userName);
    }
}

