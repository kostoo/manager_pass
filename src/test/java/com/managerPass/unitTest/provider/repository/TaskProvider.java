package com.managerPass.unitTest.provider.repository;

import com.managerPass.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskProvider extends JpaRepository<TaskEntity, Long> {
}
