package com.managerPass.unitTest.provider;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.provider.repository.RoleProvider;
import com.managerPass.unitTest.provider.repository.UserProvider;
import com.managerPass.unitTest.util.ObjectGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.managerPass.unitTest.util.ObjectGeneratorUtil.userEntityGeneration;

@Service
public class UserServiceProvider {

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private RoleProvider roleProvider;

    public UserEntity userGenerate(String userName, String email, ERole eRole, String name, String lastName,
                                   Boolean addInDb) {

        RoleEntity roleEntity = roleProvider.save(ObjectGeneratorUtil.roleGenerate(eRole));

        UserEntity user = userEntityGeneration(userName, email, roleEntity, name, lastName);
        if (addInDb) {
            return userProvider.save(user);
        } else {
            return user;
        }
    }

}
