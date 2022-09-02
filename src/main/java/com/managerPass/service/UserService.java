package com.managerPass.service;

import com.managerPass.jpa.repository_service.UserRepositoryService;
import com.managerPass.payload.request.AddUserRequest;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.payload.response.UserResponse;
import com.managerPass.util.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserConverter userConverter;
    private final UserRepositoryService userRepositoryService;

    public List<UserResponse> getUsersNameLastName(String name, String lastName, Pageable pageable) {
        return userConverter.convertListUserEntityToUserResponse(
                userRepositoryService.getUsersNameLastName(name, lastName, pageable)
        );
    }

    public UserResponse getUsersIdUser(Long idUser) {
        return userConverter.userResponseGenerate(userRepositoryService.getUserById(idUser));
    }

    public UserResponse getUsersUserName(String username) {
        return userConverter.userResponseGenerate(userRepositoryService.getUserByUsername(username));
    }

    public UserResponse addUser(AddUserRequest addUserRequest) {
        return userConverter.userResponseGenerate(userRepositoryService.addUser(addUserRequest));
    }

    public UserResponse updateUser(UserRequest userRequest, Long idUser) throws ResponseStatusException {
        return userConverter.userResponseGenerate(userRepositoryService.updateUser(userRequest, idUser));
    }

    public UserResponse postIsUserBlock(Long idUser, Boolean isAccountNonBlock) {
        return userConverter.userResponseGenerate(userRepositoryService.postIsUserBlock(idUser, isAccountNonBlock));
    }

    public void deleteUserIdUser(Long idUser) {
        userRepositoryService.deleteUserByIdUser(idUser);
    }
}
