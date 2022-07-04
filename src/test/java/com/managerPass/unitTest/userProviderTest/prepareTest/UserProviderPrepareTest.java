package com.managerPass.unitTest.userProviderTest.prepareTest;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

import static com.managerPass.unitTest.userProviderTest.UserProviderGenerationObject.roleGenerate;
import static com.managerPass.unitTest.userProviderTest.UserProviderGenerationObject.userEntityGeneration;

public class UserProviderPrepareTest extends PrepareServiceTest {

    protected UserEntity userEntityGenerate(String username, String email, Boolean addInDB) {
        RoleEntity roleEntity = roleRepository.save(roleGenerate(ERole.ROLE_ADMIN));

        UserEntity userEntity;
        if (addInDB) {
            userEntity = userEntityRepository.save(userEntityGeneration(username, email, roleEntity)
            );
        } else {
            userEntity = userEntityGeneration(username, email, roleEntity);
        }

        return userEntity;
    }
}
