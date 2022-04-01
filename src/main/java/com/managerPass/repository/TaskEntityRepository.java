package com.managerPass.repository;

import com.managerPass.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    ArrayList<TaskEntity> findAllByName(@Param("name") String name);

    void deleteById(Long id);

    @Query("SELECT task FROM TaskEntity task JOIN FETCH task.userEntity , task.priority WHERE task.userEntity.idUser = :idUser")
    ArrayList<TaskEntity> findAllByUserEntity(@Param("idUser") long idUser);

    @Query("SELECT task FROM TaskEntity task JOIN FETCH task.userEntity , task.priority" +
            " WHERE task.priority.id = :idPriority and task.userEntity.idUser = :idUser")
    ArrayList<TaskEntity> findAllByPriority(@Param("idPriority") long idPriority,@Param("idUser") long idUser);
}