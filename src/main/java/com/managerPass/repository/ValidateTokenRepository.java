package com.managerPass.repository;

import com.managerPass.entity.ValidateTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidateTokenRepository extends JpaRepository<ValidateTokenEntity, Long> {

    Optional<ValidateTokenEntity> findByToken(String token);
}
