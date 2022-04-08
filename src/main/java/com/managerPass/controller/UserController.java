package com.managerPass.controller;

import com.managerPass.entity.UserEntity;
import com.managerPass.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserEntityService userService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(path = "/lastName/{lastName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUsersByLastName(@PathVariable(value = "lastName") String lastName) {
        return ResponseEntity.ok(userService.getAllByLastName(lastName));
    }

    @GetMapping(path = "/userName/{userName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUsersByUserName(@PathVariable(value = "userName") String userName) {
        return ResponseEntity.ok(userService.getByUserName(userName));
    }

    @DeleteMapping(path = "/{idUser}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> deleteUserById(@PathVariable(value = "idUser") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
   }

    @GetMapping(path = "/users/{idUser}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserById(@PathVariable(value = "idUser") Long idUser) {
        return ResponseEntity.ok().body(userService.getById(idUser));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> postUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.postUser(userEntity));
    }

    @PutMapping()
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> putUserUpdate(@RequestBody UserEntity userEntity) {
       return ResponseEntity.ok().body(userService.putUser(userEntity));
    }

    @PostMapping(path = "/block/{idUser}",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> postUserBlock(@PathVariable(value = "idUser") Long idUser) {
        return ResponseEntity.ok(userService.postUserBlock(idUser));
    }
}
