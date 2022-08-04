package com.managerPass.service;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.exception.CustomRestExceptionHandler;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.payload.response.TaskResponse;
import com.managerPass.repository.PriorityEntityRepository;
import com.managerPass.repository.TaskEntityRepository;
import com.managerPass.repository.UserEntityRepository;
import com.managerPass.util.TaskEntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskEntityService {

    private final TaskEntityRepository taskEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PriorityEntityRepository priorityEntityRepository;

    public UserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) authentication.getPrincipal();
        } else {
            throw new RuntimeException();
        }
    }

    public List<TaskResponse> getAll() {
        return TaskEntityConverter.convertListTaskEntityToTaskResponse(taskEntityRepository.findAll());
    }

    public List<TaskResponse> getAllByName(String name) {
        return TaskEntityConverter.convertListTaskEntityToTaskResponse(taskEntityRepository.findAllByName(name));
    }

    public List<TaskResponse> getAllWithParam(String name) {
        List<TaskResponse> taskEntities;
        if (name != null) {
            taskEntities = getAllByName(name);
        } else {
            taskEntities = getAll();
        }
        return taskEntities;
    }

    public TaskResponse getByIdTask(Long id) {
        return TaskEntityConverter.taskResponseGenerate(taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task not found by Id : %x", id))
        ));
    }

    public void deleteByIdTask(Long id) {
        taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task not found by Id : %x", id))
        );
        taskEntityRepository.deleteById(id);
    }

    public TaskResponse addTask(TaskRequest taskRequest) {
        try {
            TaskEntity taskEntity = TaskEntityConverter.taskEntityGenerate(taskRequest);

            String username = getAuthentication().getUsername();
            taskEntity.setUserEntity(userEntityRepository.findByUsername(username).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
            ));

            if (taskEntity.getPriority() != null) {
                EPriority priorityName = taskEntity.getPriority().getName();
                taskEntity.setPriority(priorityEntityRepository.findByName(priorityName).orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, String.format("Not found priority name %s ", priorityName)
                        ))
                );
            } else {
                taskEntity.setPriority(priorityEntityRepository.findByName(EPriority.MEDIUM).orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, String.format("Not found priority name %s", EPriority.MEDIUM)
                        )
                ));
            }
            return TaskEntityConverter.taskResponseGenerate(taskEntityRepository.save(taskEntity));

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public TaskResponse updateTask(TaskRequest taskRequest, Long idTask) {
        try {
            if (taskEntityRepository.existsById(idTask)) {
               return TaskEntityConverter.taskResponseGenerate(taskEntityRepository.save(
                        TaskEntityConverter.taskEntityGenerate(taskRequest,idTask))
                );
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task %s not found ", idTask));
            }

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public List<TaskResponse> getAllByAuthUserWithParam(EPriority namePriority, Pageable pageable,
                                                        LocalDateTime dateTimeStart,
                                                        LocalDateTime dateTimeFinish) {

        PriorityEntity priority = priorityEntityRepository.findByName(namePriority).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", namePriority.name()))
        );

        List<TaskEntity> taskEntities;

        if (namePriority != null && dateTimeStart == null && dateTimeFinish == null) {
            taskEntities = getAllByAuthUserAndPriority(priority.getId(), pageable);
        }
        else if (namePriority == null && dateTimeStart != null && dateTimeFinish != null) {
            taskEntities = getAllByUserIdDateTimeStartAfterAndDateTimeFinishBefore(
                                        dateTimeStart, dateTimeFinish, pageable
                           ).getContent();
        }
        else if (namePriority != null && dateTimeStart != null && dateTimeFinish != null) {
            taskEntities = getAllByPriorityIdAndUserEntityIdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
                    priority.getId(), dateTimeStart, dateTimeFinish, pageable
            );
        }
        else {
            taskEntities = getAllByAuthUser();
        }

        return TaskEntityConverter.convertListTaskEntityToTaskResponse(taskEntities);
    }

    public List<TaskEntity> getAllByAuthUser() {
        String username = getAuthentication().getUsername();
        Long idUser = userEntityRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
        ).getIdUser();

        return taskEntityRepository.findAllByUserEntity_IdUser(idUser);
    }

    public List<TaskEntity> getAllByPriorityIdAndUserEntityIdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
            Long idPriority, LocalDateTime dateTimeStart, LocalDateTime dateTimeFinish, Pageable pageable
    ) {

        String username = getAuthentication().getUsername();
        Long idUser = userEntityRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
        ).getIdUser();

        return
         taskEntityRepository.findAllByPriority_IdAndUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
            idPriority, idUser, dateTimeStart, dateTimeFinish, pageable
         ).getContent();
    }

    public List<TaskEntity> getAllByAuthUserAndPriority(Long idPriority, Pageable pageable) {
        String username = getAuthentication().getUsername();
        Long idUser = userEntityRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
        ).getIdUser();

        return taskEntityRepository.findAllByPriority_IdAndUserEntity_IdUser(idPriority, idUser, pageable);
    }

    public Page<TaskEntity> getAllByUserIdDateTimeStartAfterAndDateTimeFinishBefore(LocalDateTime dateTimeStart,
                                                                                    LocalDateTime dateTimeFinish,
                                                                                    Pageable pageable) {
        String username = getAuthentication().getUsername();
        Long idUser = userEntityRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
        ).getIdUser();

        return taskEntityRepository.findAllByUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
              idUser,  dateTimeStart, dateTimeFinish, pageable
        );
    }
}
