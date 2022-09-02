package com.managerPass.jpa.repository_service;

import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.exception.CustomRestExceptionHandler;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.jpa.repository.UserEntityRepository;
import com.managerPass.util.UserConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryService {

    private final UserEntityRepository userEntityRepository;
    private final UserConverter userConverter;

    public List<UserEntity> getUsersNameLastName(String name, String lastName, Pageable pageable) {
        return userEntityRepository.findAllByNameAndLastName(name, lastName, pageable);
    }

    public UserEntity getUserById(Long idUser) {
        return userEntityRepository.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser))
        );
    }

    private void existByIdUser(Long idUser) {
        if (!userEntityRepository.existsByIdUser(idUser)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser));
        }
    }

    public UserEntity getUserByUsername(String username) {
        return userEntityRepository.findByUsername(username).orElseThrow(() ->
             new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
        );
    }

    public void deleteUserByIdUser(Long idUser) throws ResponseStatusException {
       existByIdUser(idUser);
       userEntityRepository.deleteById(idUser);
    }

    public UserEntity addUser(UserRequest userRequest) {
        try {

            UserEntity userEntity = userConverter.userEntityGenerate(userRequest);

            userEntity.setIsAccountActive(false);
            userEntity.setIsAccountNonBlock(true);

            return userEntityRepository.save(userEntity);

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public UserEntity updateUser(UserRequest userRequest, Long idUser) throws ResponseStatusException {
        return userEntityRepository.save(userConverter.userEntityGenerate(userRequest, idUser));
    }

    public UserEntity postIsUserBlock(Long idUser, Boolean isAccountNonBlock) {
        UserEntity userEntity = getUserById(idUser);
        userEntity.setIsAccountNonBlock(isAccountNonBlock);

        return userEntityRepository.save(userEntity);
    }
}
