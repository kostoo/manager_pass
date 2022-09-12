package com.managerPass.service;

import com.managerPass.jpa.service.UserRepositoryService;
import com.managerPass.payload.request.user.AddUserRequest;
import com.managerPass.payload.request.user.UpdateUserRequest;
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

    public List<UserResponse> getUsers(String name, String lastName, Pageable pageable) {
        return userConverter.convertListUserEntityToUserResponse(
                userRepositoryService.getUsers(name, lastName, pageable)
        );
    }

    public UserResponse getUsersId(Long idUser) {
        return userConverter.userResponseGenerate(userRepositoryService.getUserByIdUser(idUser));
    }

    public UserResponse getUsersUserName(String username) {
        return userConverter.userResponseGenerate(userRepositoryService.getUserByUsername(username));
    }

    public UserResponse addUser(AddUserRequest addUserRequest) {
        return userConverter.userResponseGenerate(userRepositoryService.addUser(addUserRequest));
    }

    public UserResponse updateUser(UpdateUserRequest updateUser, Long idUser) throws ResponseStatusException {
        return userConverter.userResponseGenerate(userRepositoryService.updateUser(updateUser, idUser));
    }

    public UserResponse changeStatusBlockUser(Long idUser, Boolean isAccountNonBlock) {
        return userConverter.userResponseGenerate(userRepositoryService.changeUserStatusBlock(idUser, isAccountNonBlock));
    }

    public void deleteUserId(Long idUser) {
        userRepositoryService.deleteUserByIdUser(idUser);
    }
}
