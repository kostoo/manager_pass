package com.managerPass.provider;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.provider.repository.RoleRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleProvider {

    @Autowired
    RoleRepositoryTest roleRepositoryTest;

    public RoleEntity roleGenerate(ERole eRole) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(eRole);

        return roleRepositoryTest.save(roleEntity);
    }
}
