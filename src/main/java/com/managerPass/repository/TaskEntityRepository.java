package com.managerPass.repository;

import com.managerPass.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    List<TaskEntity> findAllByName(String name);

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    List<TaskEntity> findAllByUserEntity_IdUser(Long idUser);

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    List<TaskEntity> findAllByPriority_IdAndUserEntity_IdUser(Long idPriority, Long idUser, Pageable pageable);

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    Page<TaskEntity> findAllByUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
            Long idUser, LocalDateTime dateTimeStart, LocalDateTime dateTimeFinish, Pageable pageable
    );

    @EntityGraph(value = "taskEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    Page<TaskEntity> findAllByPriority_IdAndUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
            Long idPriority, Long idUser, LocalDateTime dateTimeStart, LocalDateTime dateTimeFinish, Pageable pageable
    );


}
