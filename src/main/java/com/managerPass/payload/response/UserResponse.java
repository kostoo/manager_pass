package com.managerPass.payload.response;

import com.managerPass.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserResponse {

    @NonNull
    private Long idUser;

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 3, max = 20)
    private String lastName;

    @Size(min = 5, max = 20)
    private String username;

    @Size(min = 5)
    @Email
    private String email;

    private Set<RoleEntity> roles;

    @NonNull
    private Boolean isAccountActive;

    @NonNull
    private Boolean isAccountNonBlock;

}
