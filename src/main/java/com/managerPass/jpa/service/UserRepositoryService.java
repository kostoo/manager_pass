package com.managerPass.jpa.service;

import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.jpa.repository.UserEntityRepository;
import com.managerPass.payload.request.user.AddUserRequest;
import com.managerPass.payload.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryService {

    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> getUsers(String name, String lastName, Pageable pageable) {
        return userEntityRepository.findAllByNameAndLastName(name, lastName, pageable);
    }

    public UserEntity getUserByIdUser(Long idUser) {
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteUserByIdUser(Long idUser) throws ResponseStatusException {
       existByIdUser(idUser);
       userEntityRepository.deleteById(idUser);
    }

    public UserEntity addUser(AddUserRequest userRequest) {
        try {

            UserEntity userEntity = UserEntity.builder().username(userRequest.getUsername())
                                                        .name(userRequest.getName())
                                                        .lastName(userRequest.getLastName())
                                                        .email(userRequest.getEmail())
                                                        .roles(userRequest.getRoles())
                                                        .build();

            return userEntityRepository.save(userEntity);

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), e.getMessage());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserEntity updateUser(UpdateUserRequest updateUser, Long idUser) throws ResponseStatusException {

        UserEntity user = getUserByIdUser(idUser);

        if (updateUser.getName() != null) {
            user.setName(updateUser.getName());
        }
        if (updateUser.getLastName() != null) {
            user.setLastName(updateUser.getLastName());
        }
        if (updateUser.getUsername() != null) {
            user.setUsername(updateUser.getUsername());
        }
        if (updateUser.getEmail() != null) {
            user.setEmail(updateUser.getEmail());
        }
        if (updateUser.getRoles() != null) {
            user.setRoles(updateUser.getRoles());
        }

        return userEntityRepository.save(user);
    }

    public UserEntity changeUserStatusBlock(Long idUser, Boolean isAccountNonBlock) {
        UserEntity userEntity = getUserByIdUser(idUser);
        userEntity.setIsAccountNonBlock(isAccountNonBlock);

        return userEntityRepository.save(userEntity);
    }
}
