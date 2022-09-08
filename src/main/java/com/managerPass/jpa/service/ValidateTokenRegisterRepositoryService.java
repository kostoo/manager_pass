package com.managerPass.jpa.service;

import com.managerPass.jpa.entity.ValidateTokenEntity;
import com.managerPass.jpa.repository.ValidateTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ValidateTokenRegisterRepositoryService {

    private final ValidateTokenRepository validateTokenRepository;

    public ValidateTokenEntity addToken(ValidateTokenEntity validateTokenEntity) {
       return validateTokenRepository.save(validateTokenEntity);
    }

    public ValidateTokenEntity findByToken(String token) {
        return validateTokenRepository.findByToken(token).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Token  %s not found ", token))
        );
    }
}
