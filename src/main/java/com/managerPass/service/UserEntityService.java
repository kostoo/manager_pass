package com.managerPass.service;

import com.managerPass.entity.UserEntity;
import com.managerPass.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public ArrayList<UserEntity> getAll() {
        return (ArrayList<UserEntity>) userEntityRepository.findAll();
    }

    public ArrayList<UserEntity> getAllByLastName(String last_name) {
        return userEntityRepository.findAllByLastName(last_name);
    }

    public void deleteById(Long id) throws ResponseStatusException {
       userEntityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
               HttpStatus.NOT_FOUND, "Id : " + id)
       );
       userEntityRepository.deleteById(id);
    }

    public UserEntity getById(Long idUser) {
        return userEntityRepository.findById(idUser).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"user not found"));
    }

    public UserEntity getByUserName(String userName){
        return userEntityRepository.findByUsername(userName).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Not Found"));
    }

    public UserEntity postUser(UserEntity userEntity) {
        userEntity.setIsAccountActive(false);
        userEntity.setIsAccountNonBlock(true);
        return userEntityRepository.save(userEntity);
    }

    public UserEntity putUser(UserEntity userEntity) throws ResponseStatusException {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity postUserBlock(@PathVariable(value = "id_user") Long id_user){
        UserEntity userEntity = userEntityRepository.findById(id_user).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.OK,"Not found")
        );
        userEntity.setIsAccountNonBlock(false);

        return userEntityRepository.save(userEntity);
    }
}
