package com.managerPass.service;

import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> getUsers() {
        return userEntityRepository.findAll();
    }

    public List<UserEntity> getUsersByLastName(String lastName) {
        return userEntityRepository.findAllByLastName(lastName);
    }

    public void deleteUsersById(Long id) throws ResponseStatusException {
       userEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", id))
       );
       userEntityRepository.deleteById(id);
    }

    public UserEntity getUsersById(Long idUser) {
        return userEntityRepository.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser))
        );
    }

    public UserEntity getUsersUserName(String userName) {
        return userEntityRepository.findByUsername(userName).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found : %s", userName))
        );
    }

    public UserEntity addUser(UserRequest userRequest) {
        UserEntity userEntity = UserEntity.builder()
                                          .username(userRequest.getUsername())
                                          .name(userRequest.getName())
                                          .email(userRequest.getEmail())
                                          .roles(userRequest.getRoles())
                                          .build();

        userEntity.setIsAccountActive(false);
        userEntity.setIsAccountNonBlock(true);
        return userEntityRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) throws ResponseStatusException {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity postUserBlock(Long idUser, Boolean isBlock) {
        UserEntity userEntity = userEntityRepository.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", idUser))
        );
        userEntity.setIsAccountNonBlock(isBlock);

        return userEntityRepository.save(userEntity);
    }
}
