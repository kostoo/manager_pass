package com.managerPass.util;

import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.payload.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserEntity userEntityGenerate(UserRequest userRequest, Long idUser) {
        return UserEntity.builder().idUser(idUser)
                                   .name(userRequest.getName())
                                   .lastName(userRequest.getLastName())
                                   .email(userRequest.getEmail())
                                   .username(userRequest.getUsername())
                                   .roles(userRequest.getRoles())
                                   .build();
    }

    public UserResponse userResponseGenerate(UserEntity user) {
        return UserResponse.builder().idUser(user.getIdUser())
                                     .username(user.getUsername())
                                     .name(user.getName())
                                     .isAccountNonBlock(user.getIsAccountNonBlock())
                                     .isAccountActive(user.getIsAccountActive())
                                     .roles(user.getRoles())
                                     .email(user.getEmail())
                                     .build();
    }
    public UserEntity userEntityGenerate(UserRequest userRequest) {
        return  UserEntity.builder().username(userRequest.getUsername())
                                    .name(userRequest.getName())
                                    .lastName(userRequest.getLastName())
                                    .email(userRequest.getEmail())
                                    .roles(userRequest.getRoles())
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

    public List<UserResponse> convertListUserEntityToUserResponse(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::userResponseGenerate).collect(Collectors.toList());
    }
}
