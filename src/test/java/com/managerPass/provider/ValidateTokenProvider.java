package com.managerPass.provider;

import com.managerPass.entity.ValidateTokenEntity;
import com.managerPass.provider.repository.ValidateTokenRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenProvider {

    @Autowired
    ValidateTokenRepositoryTest validateTokenRepositoryTest;

    public ValidateTokenEntity getValidateTokenEntityByUserEntityIdUser(Long idUser) {
        return validateTokenRepositoryTest.getValidateTokenEntityByUserEntity_IdUser(idUser);
    }
}
