package com.managerPass.jpa.repository;

import com.managerPass.jpa.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = { "roles" }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserEntity> findByUsername(@Param("username")String username);

    @Query("SELECT user FROM UserEntity user WHERE (:name is null or user.name = :name) " +
            "and  (:lastName is null or user.lastName = :lastName)")
    List<UserEntity> findAllByNameAndLastName(@Param("name") String name,
                                              @Param("lastName") String lastName,
                                              Pageable pageable);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByIdUser(Long id);

}
