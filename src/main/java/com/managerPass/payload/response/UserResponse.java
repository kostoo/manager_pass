package com.managerPass.payload.response;

import com.managerPass.jpa.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserResponse {

    private Long idUser;

    private String name;

    private String lastName;

    private String username;

    @Email
    private String email;

    private Set<RoleEntity> roles;

    private Boolean isAccountActive;

    private Boolean isAccountNonBlock;

}
