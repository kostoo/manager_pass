package com.managerPass.jpa.repository;

import com.managerPass.jpa.entity.ValidateTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidateTokenRepository extends JpaRepository<ValidateTokenEntity, Long> {

    Optional<ValidateTokenEntity> findByToken(String token);
}
