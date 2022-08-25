package com.managerPass.repository;

import com.managerPass.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = { "roles"}, type = EntityGraph.EntityGraphType.LOAD)
    List<UserEntity> findAllByLastNameContains(@Param("lastName") String lastName, Pageable pageable);

    @EntityGraph(attributePaths = { "roles"}, type = EntityGraph.EntityGraphType.LOAD)
    List<UserEntity> findAllByNameContains(@Param("name") String name, Pageable pageable);

    @EntityGraph(attributePaths = { "roles"}, type = EntityGraph.EntityGraphType.LOAD)
    List<UserEntity> findAllByNameContainsAndLastNameContains(@Param("name") String name,
                                                              @Param("lastName") String lastName,
                                                              Pageable pageable);

    @EntityGraph(attributePaths = { "roles" }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserEntity> findByUsername(@Param("username")String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByIdUser(Long id);

}
