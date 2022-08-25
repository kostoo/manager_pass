package com.managerPass.service;

import com.managerPass.entity.UserEntity;
import com.managerPass.exception.CustomRestExceptionHandler;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.repository.UserEntityRepository;
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
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> getUsers() {
        return userEntityRepository.findAll();
    }

    public List<UserEntity> getUsersNameLastName(String name, String lastName, Pageable pageable) {

        List<UserEntity> listUsers;

        if (name != null & lastName != null) {
           listUsers = userEntityRepository.findAllByNameContainsAndLastNameContains(name, lastName , pageable);
        } else  if (name == null & lastName != null) {
           listUsers = userEntityRepository.findAllByLastNameContains(lastName, pageable);
        } else if (name != null) {
           listUsers = userEntityRepository.findAllByNameContains(name, pageable);
        } else {
           listUsers = userEntityRepository.findAll(pageable).getContent();
        }
        return listUsers;
    }

    private UserEntity getUserEntityById(Long idUser) {
        return userEntityRepository.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser))
        );
    }

    private void existByIdUser(Long idUser) {
        if (!userEntityRepository.existsByIdUser(idUser)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser));
        }
    }

    public UserEntity getUserEntityByUsername(String username) {
        return userEntityRepository.findByUsername(username).orElseThrow(() ->
             new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
        );
    }

    public void deleteUsersIdUser(Long idUser) throws ResponseStatusException {
       existByIdUser(idUser);
       userEntityRepository.deleteById(idUser);
    }

    public UserEntity getUsersIdUser(Long idUser) {
        return getUserEntityById(idUser);
    }

    public UserEntity getUsersUserName(String userName) {
        return getUserEntityByUsername(userName);
    }

    public UserEntity addUser(UserRequest userRequest) {
        try {

            UserEntity userEntity = UserConverter.userEntityGenerate(userRequest);

            userEntity.setIsAccountActive(false);
            userEntity.setIsAccountNonBlock(true);

            return userEntityRepository.save(userEntity);

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public UserEntity updateUser(UserRequest userRequest, Long idUser) throws ResponseStatusException {
        return userEntityRepository.save(UserConverter.UserEntityGenerate(userRequest, idUser));
    }

    public UserEntity postIsUserBlock(Long idUser, Boolean isAccountNonBlock) {
        UserEntity userEntity = getUserEntityById(idUser);
        userEntity.setIsAccountNonBlock(isAccountNonBlock);

        return userEntityRepository.save(userEntity);
    }
}
