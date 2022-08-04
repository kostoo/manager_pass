package com.managerPass.provider;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.provider.repository.RoleProvider;
import com.managerPass.provider.repository.UserProvider;
import com.managerPass.util.ObjectGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceProvider {

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private RoleProvider roleProvider;

    public UserEntity userGenerate(String userName, String email, ERole eRole, String name, String lastName,
                                   Boolean addInDb) {

        RoleEntity roleEntity = roleProvider.save(ObjectGeneratorUtil.roleGenerate(eRole));

        UserEntity user = ObjectGeneratorUtil.userEntityGeneration(userName, email, roleEntity, name, lastName);
        if (addInDb) {
            return userProvider.save(user);
        } else {
            return user;
        }
    }

}
