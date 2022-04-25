package com.managerPass.repository;

import com.managerPass.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    @Override
    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    ArrayList<TaskEntity> findAll();

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    ArrayList<TaskEntity> findAllByName(@Param("name") String name);

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    ArrayList<TaskEntity> findAllByUserEntity_IdUser(@Param("idUser")Long idUser);

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    ArrayList<TaskEntity> findAllByPriority_IdAndUserEntity_IdUser(@Param("idPriority") Long idPriority,
                                                                   @Param("idUser") Long idUser);

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    Page<TaskEntity> findAllByUserEntity_IdUser(@Param("idUser") Long idUser,Pageable pageable);

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    Page<TaskEntity> findAllByUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
            Long idUser, LocalDateTime dateTimeStart, LocalDateTime dateTimeFinish, Pageable pageable
    );
}
