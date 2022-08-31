package com.managerPass.provider.repository;

import com.managerPass.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepositoryTest extends JpaRepository<PriorityEntity, Long> {
}
