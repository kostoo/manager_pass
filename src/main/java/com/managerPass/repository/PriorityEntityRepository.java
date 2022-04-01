package com.managerPass.repository;

import com.managerPass.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriorityEntityRepository extends JpaRepository<PriorityEntity,Long> {

    @Query("SELECT priority FROM PriorityEntity  priority WHERE priority.name = :name")
    Optional<PriorityEntity> findByName(@Param("name") String name);
}
