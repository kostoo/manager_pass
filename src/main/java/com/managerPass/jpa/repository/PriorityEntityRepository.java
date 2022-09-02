package com.managerPass.jpa.repository;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriorityEntityRepository extends JpaRepository<PriorityEntity, Long> {

    Optional<PriorityEntity> findByName(EPriority name);

}
