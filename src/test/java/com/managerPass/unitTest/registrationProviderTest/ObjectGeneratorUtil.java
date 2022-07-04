package com.managerPass.unitTest.registrationProviderTest;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.payload.request.SignupRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Set;

public class ObjectGeneratorUtil {

    public static SignupRequest signupRequestGenerate(Set<ERole> eRoles) {

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(RandomStringUtils.random(10, true, false));
        signupRequest.setRole(eRoles);
        signupRequest.setEmail("test@test.ru");
        signupRequest.setPassword("password");

        return signupRequest;
    }

    public static RoleEntity generateRoleDB(ERole eRole) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(eRole);

        return roleEntity;
    }
}
