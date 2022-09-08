package com.managerPass.util;

import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.payload.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

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

    public List<UserResponse> convertListUserEntityToUserResponse(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::userResponseGenerate).collect(Collectors.toList());
    }
}
