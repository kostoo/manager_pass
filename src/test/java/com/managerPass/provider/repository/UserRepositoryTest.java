package com.managerPass.provider.repository;

import com.managerPass.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface UserRepositoryTest extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByUsernameAndIsAccountActiveEquals(String username, Boolean isAccountActive);

    UserEntity getUserEntityByUsername(@NotBlank @Size(max = 20) String username);
}
