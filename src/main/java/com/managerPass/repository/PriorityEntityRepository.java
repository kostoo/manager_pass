package com.managerPass.repository;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PriorityEntityRepository extends JpaRepository<PriorityEntity,Long> {

    Optional<PriorityEntity> findByName(EPriority name);
}
