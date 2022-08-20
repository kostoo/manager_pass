package com.managerPass.service;

import com.managerPass.entity.UserEntity;
import com.managerPass.exception.CustomRestExceptionHandler;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.payload.response.UserResponse;
import com.managerPass.repository.UserEntityRepository;
import com.managerPass.util.UserEntityConverter;
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

    public List<UserResponse> getUsersNameLastName(String name, String lastName, Pageable pageable) {

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
        return UserEntityConverter.convertUserEntityToUserResponse(listUsers);
    }

    public void deleteUsersIdUser(Long IdUser) throws ResponseStatusException {
       userEntityRepository.findById(IdUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", IdUser))
       );
       userEntityRepository.deleteById(IdUser);
    }

    public UserResponse getUsersIdUser(Long idUser) {
        return UserEntityConverter.UserResponseGenerate(userEntityRepository.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser))
        ));
    }

    public UserResponse getUsersUserName(String userName) {
        return UserEntityConverter.UserResponseGenerate(userEntityRepository.findByUsername(userName).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found : %s", userName))
        ));
    }

    public UserResponse addUser(UserRequest userRequest) {
        try {

            UserEntity userEntity = UserEntityConverter.userEntityGenerate(userRequest);

            userEntity.setIsAccountActive(false);
            userEntity.setIsAccountNonBlock(true);

            return UserEntityConverter.UserResponseGenerate(userEntityRepository.save(userEntity));

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public UserResponse updateUser(UserRequest userRequest, Long idUser) throws ResponseStatusException {
        return UserEntityConverter.UserResponseGenerate(
                userEntityRepository.save(UserEntityConverter.UserEntityGenerate(userRequest, idUser))
        );
    }

    public UserResponse postIsUserBlock(Long idUser, Boolean isAccountNonBlock) {
        UserEntity userEntity = userEntityRepository.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser))
        );
        userEntity.setIsAccountNonBlock(isAccountNonBlock);

        return UserEntityConverter.UserResponseGenerate(userEntityRepository.save(userEntity));
    }
}
