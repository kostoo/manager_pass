package com.managerPass.service;

import com.managerPass.entity.ValidateTokenEntity;
import com.managerPass.repository.ValidateTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ValidateTokenRegisterEntityService {

    private final ValidateTokenRepository validateTokenRepository;

    public ValidateTokenEntity addToken(ValidateTokenEntity validateTokenEntity) {
       return validateTokenRepository.save(validateTokenEntity);
    }

    public ValidateTokenEntity findByToken(String token) {
        return validateTokenRepository.findByToken(token).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Token  %s not found ")
        );
    }
}
