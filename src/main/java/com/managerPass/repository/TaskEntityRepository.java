package com.managerPass.repository;

import com.managerPass.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    ArrayList<TaskEntity> findAllByName(@Param("name") String name);

    @Query("SELECT task FROM TaskEntity task  WHERE task.userEntity.idUser = :idUser")
    ArrayList<TaskEntity> findAllByUserEntity(@Param("idUser") Long idUser);

    @Query("SELECT task FROM TaskEntity task WHERE task.priority.id = :idPriority and task.userEntity.idUser = :idUser")
    ArrayList<TaskEntity> findAllByPriority(@Param("idPriority") Long idPriority, @Param("idUser") Long idUser);

    @Query(value = "SELECT task FROM TaskEntity task  WHERE task.userEntity.idUser = :idUser",
           countQuery = "SELECT count(task) FROM TaskEntity task  WHERE task.userEntity.idUser = :idUser")
    Page<TaskEntity> findAllByUserEntityPage(@Param("idUser") Long idUser,Pageable pageable);

    @Query(value = "SELECT task FROM TaskEntity task WHERE task.userEntity.idUser = :idUser" +
                   " and task.dateTimeStart >= :dateTimeStart and task.dateTimeFinish <= :dateTimeFinish",
    countQuery = "SELECT count(task) FROM TaskEntity task WHERE task.userEntity.idUser = :idUser" +
                 " and task.dateTimeStart >= :dateTimeStart and task.dateTimeFinish <= :dateTimeFinish" )
    Page<TaskEntity> findAllByUserEntityAndDateTimeStartIsAfterAndDateTimeFinishBefore(LocalDateTime dateTimeStart,
                                                                                       LocalDateTime dateTimeFinish,
                                                                                       Pageable pageable, Long idUser);
}
