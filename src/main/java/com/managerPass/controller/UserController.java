package com.managerPass.controller;

import com.managerPass.entity.UserEntity;
import com.managerPass.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserEntityService userService;

    @GetMapping(path = "/users",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUserGetAll() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping(path = "/user/lastName/{lastName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUserByLastName(@PathVariable(value = "lastName") String lastName) {
        return ResponseEntity.ok(userService.getAllUserByLastName(lastName));
    }

    @GetMapping(path = "/user/userName/{userName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserByUserName(@PathVariable(value = "userName") String userName) {
        return ResponseEntity.ok(userService.getUserByUserName(userName));
    }

    @DeleteMapping(path = "/user/{id_user}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> deleteUserById(@PathVariable(value = "id_user") Long id)  {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
   }

    @GetMapping(path = "/user/{id_user}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserById(@PathVariable(value = "id_user") Long id_user) {
        Optional<UserEntity> user = userService.getUserById(id_user);

        return ResponseEntity.ok().body(user.get());
    }

    @PostMapping(path = "/user",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> postAddUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.addUser(userEntity));
    }

    @PutMapping(path = "/user")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> putUserUpdate(@RequestBody UserEntity userEntity) {
        userService.updateUser(userEntity);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/user/block/{id_user}",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> postUserBlock(@PathVariable(value = "id_user") Long id_user){
        return ResponseEntity.ok(userService.postUserBlock(id_user));
    }
}
