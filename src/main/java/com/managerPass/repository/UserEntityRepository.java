package com.managerPass.repository;

import com.managerPass.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"idUser", "name", "lastName","username","email","password","roles"},
                 type = EntityGraph.EntityGraphType.FETCH)
    ArrayList<UserEntity> findAllByLastName(@Param("lastName") String lastName);

    @EntityGraph(attributePaths = {"idUser", "name", "lastName", "username", "email", "password", "roles"},
                 type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserEntity> findByUsername(@Param("username")String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
