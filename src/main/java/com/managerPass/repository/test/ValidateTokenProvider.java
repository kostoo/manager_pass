package com.managerPass.repository.test;

import com.managerPass.entity.ValidateTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidateTokenProvider extends JpaRepository<ValidateTokenEntity, Long> {
    ValidateTokenEntity getValidateTokenEntityByUserEntity_IdUser(Long userEntity_idUser);
}
