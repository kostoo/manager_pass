package com.managerPass.service;

import com.managerPass.entity.UserEntity;
import com.managerPass.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public ArrayList<UserEntity> getAllUser() {
        return userEntityRepository.findAll();
    }

    public ArrayList<UserEntity> getAllUserByLastName(String last_name) {
        return userEntityRepository.findAllByLastName(last_name);
    }

    public void deleteUserById(Long id) throws ResponseStatusException {
       userEntityRepository.findById(id)
                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id : " + id));
       userEntityRepository.deleteById(id);
    }

    public Optional<UserEntity> getUserById(Long id_user) {
        return userEntityRepository.findUserEntityByIdUser(id_user);
    }

    public UserEntity getUserByUserName(String username){
        return userEntityRepository.findByUsername(username)
                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }
    public UserEntity addUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) throws ResponseStatusException {
        Optional.of(userEntityRepository.findById(userEntity.getIdUser())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Id : " + userEntity.getIdUser()))
        );
        return userEntityRepository.save(userEntity);
    }

    public UserEntity postUserBlock(@PathVariable(value = "id_user") Long id_user){
        UserEntity userEntity = userEntityRepository.findById(id_user)
                                                    .orElseThrow(() -> new ResponseStatusException(
                                                            HttpStatus.OK,"Not found")
                                                    );
        userEntity.setIsAccountNonBlock(false);
        return userEntityRepository.save(userEntity);
    }
}
