package com.managerPass.unitTest.userProviderTest;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.UserEntity;

import java.util.Set;

public class UserProviderGenerationObject {

    public static UserEntity userEntityGeneration(String userName, String email, RoleEntity roleEntity) {
        return  UserEntity.builder()
                .username(userName)
                .email(email)
                .name("test name")
                .lastName("test lastName")
                .roles(Set.of(roleEntity))
                .isAccountNonBlock(true)
                .isAccountActive(true)
                .build();
    }

    public static RoleEntity roleGenerate(ERole eRole) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(eRole);

        return roleEntity;
    }
}
