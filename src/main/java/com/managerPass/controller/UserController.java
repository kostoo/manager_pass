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
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(path = "/lastName/{lastName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUsersLastName(@PathVariable (value = "lastName") String lastName) {
        return ResponseEntity.ok(userService.getUsersByLastName(lastName));
    }

    @GetMapping(path = "/userName/{userName}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUsersUserName(@PathVariable (value = "userName") String userName) {
        return ResponseEntity.ok(userService.getUsersUserName(userName));
    }

    @DeleteMapping(path = "/{idUser}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> deleteUsersIdUser(@PathVariable (value = "idUser") Long id) {
        userService.deleteUsersById(id);
        return ResponseEntity.ok().build();
   }

    @GetMapping(path = "/{idUser}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserIdUser(@PathVariable (value = "idUser") Long idUser) {
        return ResponseEntity.ok().body(userService.getUsersById(idUser));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> postUsers(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.postUser(userEntity));
    }

    @PutMapping
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> putUsers(@RequestBody UserEntity userEntity) {
       return ResponseEntity.ok().body(userService.putUser(userEntity));
    }

    @PostMapping(path = "/block/{idUser}/{isBlock}",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> postUsersBlockIdUserIsBlock(@PathVariable (value = "idUser") Long idUser,
                                                                  @PathVariable (value = "isBlock") Boolean isBlock) {
        return ResponseEntity.ok(userService.postUserBlock(idUser, isBlock));
    }
}
