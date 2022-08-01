package com.managerPass.util;

import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.payload.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserEntityConverter {

    public static UserEntity UserEntityGenerate(UserRequest userRequest, Long idUser) {
        return UserEntity.builder().idUser(idUser)
                                   .name(userRequest.getName())
                                   .lastName(userRequest.getLastName())
                                   .email(userRequest.getEmail())
                                   .username(userRequest.getUsername())
                                   .roles(userRequest.getRoles())
                                   .build();
    }

    public static UserResponse UserResponseGenerate(UserEntity user) {
        return UserResponse.builder().idUser(user.getIdUser())
                                     .username(user.getUsername())
                                     .name(user.getName())
                                     .isAccountNonBlock(user.getIsAccountNonBlock())
                                     .isAccountActive(user.getIsAccountActive())
                                     .roles(user.getRoles())
                                     .email(user.getEmail())
                                     .build();
    }
    public static UserEntity userEntityGenerate(UserRequest userRequest) {
        return  UserEntity.builder().username(userRequest.getUsername())
                                    .name(userRequest.getName())
                                    .lastName(userRequest.getLastName())
                                    .email(userRequest.getEmail())
                                    .roles(userRequest.getRoles())
                                    .build();
    }

    public static List<UserResponse> convertUserEntityToUserResponse(List<UserEntity> userEntities) {
        return userEntities.stream().map(UserEntityConverter::UserResponseGenerate).collect(Collectors.toList());
    }
}
