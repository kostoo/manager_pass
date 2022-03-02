package com.managerPass.controller;


import com.managerPass.entity.UserEntity;
import com.managerPass.entity.UserSecurity;
import com.managerPass.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserEntityService userService;

    @GetMapping(path = "/getAllUser",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUserGetAll() {
        List<UserEntity> users = new ArrayList<>(userService.getAllUser());

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/getUserByName/{lastName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUserByLastName(@PathVariable(value = "lastName") String lastName) {
        List<UserEntity> users = userService.getAllUserByLastName(lastName);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(users);
    }
    @GetMapping(path = "/getUserByUserName/{userName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserByUserName(@PathVariable(value = "userName") String userName) {
      UserEntity userEntity = userService.getUserByUserName(userName);


        return ResponseEntity.ok(userEntity);
    }
    @DeleteMapping(path = "/deleteUser/{id_user}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> deleteUserById(@PathVariable(value = "id_user") Long id)  {
        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/getUser/{id_user}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserById(@PathVariable(value = "id_user") Long id_user) {
        Optional<UserEntity> user = userService.getUserById(id_user);

        return ResponseEntity.ok().body(user.get());
    }

    @PostMapping(path = "/addUser",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity postAddUser(@RequestBody UserEntity userEntity) {
        UserEntity returnEntity = userService.addUser(userEntity);

        return ResponseEntity.ok(returnEntity);
    }

    @PutMapping(path = "/updateUser")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity putUserUpdate(@RequestBody UserEntity userEntity) {
        userService.updateUser(userEntity);

        return new ResponseEntity(HttpStatus.OK);
    }
}
