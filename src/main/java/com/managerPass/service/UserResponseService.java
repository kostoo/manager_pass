package com.managerPass.service;

import com.managerPass.payload.request.UserRequest;
import com.managerPass.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.managerPass.util.UserConverter.UserResponseGenerate;
import static com.managerPass.util.UserConverter.convertListUserEntityToUserResponse;

@Service
@RequiredArgsConstructor
public class UserResponseService {

    private final UserEntityService userEntityService;

    public List<UserResponse> getUsersNameLastName(String name, String lastName, Pageable pageable) {
        return convertListUserEntityToUserResponse(userEntityService.getUsersNameLastName(name, lastName, pageable));
    }

    public UserResponse getUsersIdUser(Long idUser) {
        return UserResponseGenerate(userEntityService.getUsersIdUser(idUser));
    }

    public UserResponse getUsersUserName(String userName) {
        return UserResponseGenerate(userEntityService.getUsersUserName(userName));
    }

    public UserResponse addUser(UserRequest userRequest) {
        return UserResponseGenerate(userEntityService.addUser(userRequest));
    }

    public UserResponse updateUser(UserRequest userRequest, Long idUser) throws ResponseStatusException {
        return UserResponseGenerate(userEntityService.updateUser(userRequest, idUser));
    }

    public UserResponse postIsUserBlock(Long idUser, Boolean isAccountNonBlock) {
        return UserResponseGenerate(userEntityService.postIsUserBlock(idUser, isAccountNonBlock));
    }
}
