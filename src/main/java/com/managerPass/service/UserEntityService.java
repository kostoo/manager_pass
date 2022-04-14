package com.managerPass.service;

import com.managerPass.entity.UserEntity;
import com.managerPass.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public ArrayList<UserEntity> getUsers() {
        return (ArrayList<UserEntity>) userEntityRepository.findAll();
    }

    public ArrayList<UserEntity> getUsersByLastName(String last_name) {
        return userEntityRepository.findAllByLastName(last_name);
    }

    public void deleteUsersById(Long id) throws ResponseStatusException {
       userEntityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
               HttpStatus.NOT_FOUND, "Id : " + id)
       );
       userEntityRepository.deleteById(id);
    }

    public UserEntity getUsersById(Long idUser) {
        return userEntityRepository.findById(idUser).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"user not found" + idUser));
    }

    public UserEntity getUsersUserName(String userName) {
        return userEntityRepository.findByUsername(userName).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "user Not Found" + userName));
    }

    public UserEntity postUser(UserEntity userEntity) {
        userEntity.setIsAccountActive(false);
        userEntity.setIsAccountNonBlock(true);
        return userEntityRepository.save(userEntity);
    }

    public UserEntity putUser(UserEntity userEntity) throws ResponseStatusException {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity postUserBlock(Long id_user,Boolean isBlock) {
        UserEntity userEntity = userEntityRepository.findById(id_user).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found" + id_user)
        );
        userEntity.setIsAccountNonBlock(isBlock);

        return userEntityRepository.save(userEntity);
    }
}
