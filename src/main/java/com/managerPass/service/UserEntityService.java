package com.managerPass.service;

import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    public void deleteUsersIdUser(Long IdUser) throws ResponseStatusException {
       userEntityRepository.findById(IdUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found Id : %x", IdUser))
       );
       userEntityRepository.deleteById(IdUser);
    }

    public UserEntity getUsersIdUser(Long idUser) {
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
                                          .lastName(userRequest.getLastName())
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
