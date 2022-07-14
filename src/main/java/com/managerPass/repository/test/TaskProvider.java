package com.managerPass.repository.test;

import com.managerPass.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskProvider extends JpaRepository<TaskEntity, Long> {
}
