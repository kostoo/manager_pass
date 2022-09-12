package com.managerPass.controller;

import com.managerPass.payload.request.user.AddUserRequest;
import com.managerPass.payload.request.user.UpdateUserRequest;
import com.managerPass.payload.response.UserResponse;
import com.managerPass.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") @Min(1) @Max(100)
                                                       int sizePage,

                                                       @RequestParam(required = false) @Size(max = 100) String name,

                                                       @RequestParam(required = false) @Size(max = 100)
                                                       String lastName) {

        Pageable pageable = PageRequest.of(page, sizePage);

        return ResponseEntity.ok(userService.getUsers(name, lastName, pageable));
    }

    @DeleteMapping(path = "/{idUser}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<?> deleteUsersIdUser(@PathVariable(value = "idUser") @Min(1) Long idUser) {
        userService.deleteUserId(idUser);
        return ResponseEntity.ok().build();
   }

    @GetMapping(path = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserIdUser(@PathVariable(value = "idUser") @Min(1) Long idUser) {
        return ResponseEntity.ok().body(userService.getUsersId(idUser));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> postUsers(@Valid @RequestBody AddUserRequest userRequest) {
        return ResponseEntity.ok(userService.addUser(userRequest));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> putUsers(@Valid @RequestBody UpdateUserRequest updateUser, Long idUser) {
       return ResponseEntity.ok().body(userService.updateUser(updateUser, idUser));
    }

    @PatchMapping(path = "/block", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> patchUsersBlockIdUser(@RequestParam @Min(1) Long idUser) {
        return ResponseEntity.ok(userService.changeStatusBlockUser(idUser, true));
    }

    @PatchMapping(path = "/unblock", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> patchUsersUnBlockIdUser(@RequestParam @Min(1) Long idUser) {
        return ResponseEntity.ok(userService.changeStatusBlockUser(idUser, false));
    }
}
