package com.managerPass.service;


import com.managerPass.entity.TaskEntity;
import com.managerPass.repository.TaskEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskEntityService {

    private final TaskEntityRepository taskEntityRepository;

    public ArrayList<TaskEntity> findAll() {
        return taskEntityRepository.findAll();
    }

    public ArrayList<TaskEntity> findAllByName( String name) {
        return taskEntityRepository.findAllByName(name);
    }

    public TaskEntity findTaskEntityByIdTask( Long id) {
      TaskEntity taskEntity = taskEntityRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                                                                                  (HttpStatus.NOT_FOUND, "Id : " + id));

        return taskEntity;
    }

    public void deleteTaskEntityByIdTask(Long id) {
         taskEntityRepository.findById(id).orElseThrow(() ->
                                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Id : " + id));

        taskEntityRepository.deleteById(id);
    }

    public TaskEntity addTask(TaskEntity taskEntity){
      return taskEntityRepository.save(taskEntity);
    }

    public TaskEntity updateTask(TaskEntity taskEntity){
        Optional.of(taskEntityRepository.findById(taskEntity.getIdTask())
                                                            .orElseThrow(() -> new ResponseStatusException
                                                              (HttpStatus.NOT_FOUND, "Id : " + taskEntity.getIdTask())));

        return taskEntityRepository.save(taskEntity);
    }
}
