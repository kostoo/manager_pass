package com.managerPass.repository;

import com.managerPass.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    ArrayList<UserEntity> findAll();

    ArrayList<UserEntity> findAllByLastName(@Param("last_name") String last_name);

    UserEntity findUserEntityByIdUser(@Param("id_user") Long id);

    UserEntity deleteUserEntityByIdUser(@Param("id_user") Long id);

    Optional<Object> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
