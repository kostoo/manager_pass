package com.managerPass.repository;

import com.managerPass.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    ArrayList<TaskEntity> findAll();

    ArrayList<TaskEntity> findAllByName(@Param("name") String name);

    Optional<TaskEntity> findTaskEntityByIdTask(@Param("id_task") Long id);

    TaskEntity deleteTaskEntityByIdTask(@Param("id_task") Long id);




}