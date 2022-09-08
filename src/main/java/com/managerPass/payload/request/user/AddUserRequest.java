package com.managerPass.payload.request.user;

import com.managerPass.jpa.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddUserRequest {

    @Size(min = 1, max = 255)
    private String name;

    @Size(min = 1, max = 255)
    private String lastName;

    @Size(min = 5, max = 20)
    private String username;

    @Size(min = 5, max = 50)
    @Email
    private String email;

    private Set<RoleEntity> roles ;
}
