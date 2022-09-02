package com.managerPass.payload.request;

import com.managerPass.jpa.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddUserRequest {

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @Size(min = 5, max = 20)
    private String username;

    @Size(min = 5, max = 50)
    @Email
    private String email;

    private Set<RoleEntity> roles ;
}
