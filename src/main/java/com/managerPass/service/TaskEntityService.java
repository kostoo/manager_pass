package com.managerPass.service;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.TaskEntity;
import com.managerPass.repository.PriorityEntityRepository;
import com.managerPass.repository.TaskEntityRepository;
import com.managerPass.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TaskEntityService {

    private final TaskEntityRepository taskEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PriorityEntityRepository priorityEntityRepository;
    private SecurityContext context;

    public ArrayList<TaskEntity> getAll() {
        return  taskEntityRepository.findAll();
    }

    public ArrayList<TaskEntity> getAllByName(String name) {
        return taskEntityRepository.findAllByName(name);
    }

    public TaskEntity getByIdTask(Long id) {
        return taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found by Id : " + id)
        );
    }

    public void deleteByIdTask(Long id) {
        taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found by Id : " + id)
        );
        taskEntityRepository.deleteById(id);
    }

    public TaskEntity postTask(TaskEntity taskEntity) {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        taskEntity.setUserEntity(userEntityRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found " + authentication.getName()))
        );

        if (taskEntity.getPriority() != null) {
            taskEntity.setPriority(priorityEntityRepository.findByName(taskEntity.getPriority().getName()).orElseThrow(
            ()-> new ResponseStatusException(
                    HttpStatus.NOT_FOUND," Not found priority name " + taskEntity.getPriority().getName())
            ));
        } else {
            taskEntity.setPriority(priorityEntityRepository.findByName(EPriority.MEDIUM).orElseThrow(()->
                    new ResponseStatusException(HttpStatus.NOT_FOUND," Not found priority name "+ EPriority.MEDIUM))
            );
        }
        return taskEntityRepository.save(taskEntity);
    }

    public TaskEntity putTask(TaskEntity taskEntity) {
        return taskEntityRepository.save(taskEntity);
    }

    public ArrayList<TaskEntity> getAllByAuthUser() {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long idUser = userEntityRepository.findByUsername(authentication.getName()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found "+ authentication.getName())
        ).getIdUser();

        return taskEntityRepository.findAllByUserEntity_IdUser(idUser);
    }

    public ArrayList<TaskEntity> getAllByAuthUserAndPriority(Long idPriority) {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long idUser = userEntityRepository.findByUsername(authentication.getName()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found " + authentication.getName())
        ).getIdUser();

        return taskEntityRepository.findAllByPriority_IdAndUserEntity_IdUser(idPriority,idUser);
    }

    public Page<TaskEntity> findAllByUserEntityPage(Pageable pageable) {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long idUser = userEntityRepository.findByUsername(authentication.getName()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found "+ authentication.getName())
        ).getIdUser();

        return taskEntityRepository.findAllByUserEntity_IdUser(idUser,pageable);
    }

    public Page<TaskEntity> findAllByUserIdDateTimeStartAfterAndDateTimeFinishBefore(LocalDateTime dateTimeStart,
                                                                                     LocalDateTime dateTimeFinish,
                                                                                     Pageable pageable) {
        context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long idUser = userEntityRepository.findByUsername(authentication.getName()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found "+ authentication.getName())
        ).getIdUser();

        return taskEntityRepository.findAllByUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
                idUser,  dateTimeStart, dateTimeFinish, pageable
        );
    }
}
