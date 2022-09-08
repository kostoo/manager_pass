package com.managerPass.provider;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.RoleEntity;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.payload.request.user.UserRequest;
import com.managerPass.provider.repository.UserRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserProvider {

    @Autowired
    private UserRepositoryTest userRepositoryTest;

    @Autowired
    private RoleProvider roleProvider;

    public UserEntity userGenerate(String userName, String email, ERole eRole, String name, String lastName) {

        RoleEntity roleEntity = roleProvider.roleGenerate(eRole);

        return userEntityGeneration(userName, email, roleEntity, name, lastName);
    }

    public UserEntity userGenerateDb(String userName, String email, ERole eRole, String name, String lastName) {

        RoleEntity roleEntity = roleProvider.roleGenerate(eRole);

        UserEntity user = userEntityGeneration(userName, email, roleEntity, name, lastName);

        return userRepositoryTest.save(user);

    }


    public UserEntity userEntityGeneration(String username, String email, RoleEntity roleEntity, String name,
                                           String lastName) {
        return  UserEntity.builder()
                          .name(name)
                          .lastName(lastName)
                          .username(username)
                          .email(email)
                          .roles(Set.of(roleEntity))
                          .isAccountNonBlock(true)
                          .isAccountActive(true)
                          .build();
    }

    public UserRequest userRequestGenerate(UserEntity user) {
        return UserRequest.builder()
                          .username(user.getUsername())
                          .name(user.getName())
                          .roles(user.getRoles())
                          .email(user.getEmail())
                          .build();
    }

    public Boolean isUserExistByUsername(String username) {
        return userRepositoryTest.existsByUsername(username);
    }
}
