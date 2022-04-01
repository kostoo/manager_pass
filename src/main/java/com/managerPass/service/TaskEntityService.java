package com.managerPass.service;

import com.managerPass.entity.TaskEntity;
import com.managerPass.repository.PriorityEntityRepository;
import com.managerPass.repository.TaskEntityRepository;
import com.managerPass.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskEntityService {

    private final TaskEntityRepository taskEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PriorityEntityRepository priorityEntityRepository;
    private SecurityContext context;

    public ArrayList<TaskEntity> findAll() {
        return (ArrayList<TaskEntity>) taskEntityRepository.findAll();
    }

    public ArrayList<TaskEntity> findAllByName( String name) {
        return taskEntityRepository.findAllByName(name);
    }

    public TaskEntity findTaskEntityByIdTask( Long id) {

        return taskEntityRepository.findById(id)
                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id : " + id));
    }

    public void deleteTaskEntityByIdTask(Long id) {
        taskEntityRepository.findById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id : " + id));
        taskEntityRepository.deleteById(id);
    }

    public TaskEntity addTask(TaskEntity taskEntity) {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        taskEntity.setUserEntity(userEntityRepository.findByUsername(authentication.getName()).get());

        if (taskEntity.getPriority() != null){
            taskEntity.setPriority(priorityEntityRepository.findByName(taskEntity.getPriority().getName()).get());
        }else{
            taskEntity.setPriority(priorityEntityRepository.findByName("MEDIUM").get());
        }
        return taskEntityRepository.save(taskEntity);
    }

    public TaskEntity updateTask(TaskEntity taskEntity) {
        Optional.of(taskEntityRepository.findById(taskEntity.getIdTask())
                                        .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Id : " + taskEntity.getIdTask()))
        );

        return taskEntityRepository.save(taskEntity);
    }

    public ArrayList<TaskEntity> findAllByUserEntity() {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long idUser = userEntityRepository.findByUsername(authentication.getName())
                                                                        .orElseThrow(()-> new ResponseStatusException(
                                                                           HttpStatus.NOT_FOUND, "user not found"))
                                                                        .getIdUser();
      return taskEntityRepository.findAllByUserEntity(idUser);
    }

    public ArrayList<TaskEntity> findAllByUserAndPriority(@Param("idPriority") long idPriority) {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long idUser = userEntityRepository.findByUsername(authentication.getName())
                                          .orElseThrow(()-> new ResponseStatusException(
                                                  HttpStatus.NOT_FOUND, "user not found"))
                                          .getIdUser();
        return taskEntityRepository.findAllByPriority(idPriority,idUser);
    }
}
