package com.managerPass.payload.request.user;

import com.managerPass.jpa.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateUserRequest {

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String lastName;

    @Size(max = 20)
    private String username;

    @Size(max = 50)
    private String email;

    private Set<RoleEntity> roles ;
}
