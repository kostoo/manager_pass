package com.managerPass.provider.repository;

import com.managerPass.jpa.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepositoryTest extends JpaRepository<PriorityEntity, Long> {
}
