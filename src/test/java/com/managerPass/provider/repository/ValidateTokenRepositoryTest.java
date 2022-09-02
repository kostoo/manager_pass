package com.managerPass.provider.repository;

import com.managerPass.jpa.entity.ValidateTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidateTokenRepositoryTest extends JpaRepository<ValidateTokenEntity, Long> {

    ValidateTokenEntity getValidateTokenEntityByUserEntity_IdUser(Long userEntity_idUser);
}
