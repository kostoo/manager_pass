package com.managerPass.jpa.repository;

import com.managerPass.jpa.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT task FROM TaskEntity task WHERE (:name is null or task.name = :name)")
    List<TaskEntity> findAllByName(@Param("name") String name);

    @Query("SELECT task FROM TaskEntity task WHERE (task.userEntity.idUser = :idUser) AND " +
            "((:idPriority is null or  task.priority.id = :idPriority)" +
            "AND (:idPriority is null or  task.priority.id = :idPriority)) " +
            "OR ((:dateTimeStart is null or  task.dateTimeStart >= :dateTimeStart) " +
            "AND (:dateTimeFinish is null or  task.dateTimeFinish =< :dateTimeFinish))")
    Page<TaskEntity> findAllByUserEntityIdUserAndPriorityIdOrDateTimeStartAndDateTimeFinish(
                                                                    @Param("idPriority")Long idPriority,
                                                                    @Param("idUser")Long idUser,
                                                                    @Param("dateTimeStart")LocalDateTime dateTimeStart,
                                                                    @Param("dateTimeStart")LocalDateTime dateTimeFinish,
                                                                    Pageable pageable
    );

}
