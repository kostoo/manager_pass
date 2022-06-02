package com.managerPass.controller;

import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.UserRequest;
import com.managerPass.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserEntityService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getUsersLastName(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int sizePage,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String lastName) {
        Pageable pageable = PageRequest.of(page, sizePage);

        return ResponseEntity.ok(userService.getUsersNameLastName(name, lastName , pageable));
    }

    @GetMapping(path = "/userName/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUsersUserName(@PathVariable String userName) {
        return ResponseEntity.ok(userService.getUsersUserName(userName));
    }

    @DeleteMapping(path = "/{idUser}")
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> deleteUsersIdUser(@PathVariable (value = "idUser") Long id) {
        userService.deleteUsersIdUser(id);
        return ResponseEntity.ok().build();
   }

    @GetMapping(path = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserIdUser(@PathVariable (value = "idUser") Long idUser) {
        return ResponseEntity.ok().body(userService.getUsersIdUser(idUser));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> postUsers(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.addUser(userRequest));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> putUsers(@Valid @RequestBody UserEntity userEntity) {
       return ResponseEntity.ok().body(userService.updateUser(userEntity));
    }

    @PostMapping(path = "/block",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ADMIN')")
    public ResponseEntity<UserEntity> postUsersBlockIdUserIsBlock(@RequestParam  Long idUser,
                                                                  @RequestParam  Boolean isBlock) {
        return ResponseEntity.ok(userService.postUserBlock(idUser, isBlock));
    }
}
