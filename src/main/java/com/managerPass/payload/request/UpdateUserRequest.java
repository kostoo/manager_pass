package com.managerPass.payload.request;

import com.managerPass.jpa.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateUserRequest {

    private String name;

    private String lastName;

    private String username;

    private String email;

    private Set<RoleEntity> roles ;
}
