package com.managerPass.controller;

import com.managerPass.payload.request.UserRequest;
import com.managerPass.payload.response.UserResponse;
import com.managerPass.service.UserEntityService;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserEntityService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsersNameLastName(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int sizePage,

                                                                 @RequestParam(required = false) String name,

                                                                 @RequestParam(required = false) String lastName) {
        Pageable pageable = PageRequest.of(page, sizePage);

        return ResponseEntity.ok(userService.getUsersNameLastName(name, lastName , pageable));
    }

    @GetMapping(path = "/username", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUsersUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUsersUserName(username));
    }

    @DeleteMapping(path = "/{idUser}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<?> deleteUsersIdUser(@PathVariable (value = "idUser") Long id) {
        userService.deleteUsersIdUser(id);
        return ResponseEntity.ok().build();
   }

    @GetMapping(path = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserIdUser(@PathVariable (value = "idUser") Long idUser) {
        return ResponseEntity.ok().body(userService.getUsersIdUser(idUser));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> postUsers(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.addUser(userRequest));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> putUsers(@Valid @RequestBody UserRequest userRequest, Long idUser) {
       return ResponseEntity.ok().body(userService.updateUser(userRequest, idUser));
    }

    @PatchMapping(path = "/block", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> postUsersBlockIdUser(@RequestParam Long idUser) {
        return ResponseEntity.ok(userService.postIsUserBlock(idUser, true));
    }

    @PatchMapping(path = "/unblock", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserResponse> patchUsersUnBlockIdUser(@RequestParam Long idUser) {
        return ResponseEntity.ok(userService.postIsUserBlock(idUser, false));
    }
}
