package com.managerPass.service;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.exception.CustomRestExceptionHandler;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.repository.TaskEntityRepository;
import com.managerPass.util.TaskConverter;
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
    private final UserEntityService userEntityService;
    private final PriorityEntityService priorityEntityService;

    public UserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) authentication.getPrincipal();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is UnAuthorized");
        }
    }

    public List<TaskEntity> getAll() {
        return taskEntityRepository.findAll();
    }

    public List<TaskEntity> getAllByName(String name) {
        return taskEntityRepository.findAllByName(name);
    }

    public List<TaskEntity> getAllName(String name) {
        List<TaskEntity> taskEntities;
        if (name != null) {
            taskEntities = getAllByName(name);
        } else {
            taskEntities = getAll();
        }
        return taskEntities;
    }

    private TaskEntity getTaskEntityById(Long id) {
        return taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task not found by Id : %x", id))
        );
    }

    public TaskEntity getByIdTask(Long id) {
        return getTaskEntityById(id);
    }

    public void deleteByIdTask(Long id) {
        getTaskEntityById(id);
        taskEntityRepository.deleteById(id);
    }

    public TaskEntity addTask(TaskRequest taskRequest) {
        try {
            TaskEntity taskEntity = TaskConverter.taskEntityGenerate(taskRequest);

            String username = getAuthentication().getUsername();
            taskEntity.setUserEntity(userEntityService.getUserEntityByUsername(username));

            if (taskEntity.getPriority() != null) {
                EPriority priorityName = taskEntity.getPriority().getName();
                taskEntity.setPriority(priorityEntityService.getPriorityByName(priorityName));
            } else {
                taskEntity.setPriority(priorityEntityService.getPriorityByName(EPriority.MEDIUM));
            }

            return taskEntityRepository.save(taskEntity);

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public TaskEntity updateTask(TaskRequest taskRequest, Long idTask) {
        try {
            if (taskEntityRepository.existsById(idTask)) {
               return taskEntityRepository.save(TaskConverter.taskEntityGenerate(taskRequest,idTask));
            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task %s not found", idTask));
            }

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public List<TaskEntity> getAllByAuthUserWithEPriorityPageableDateTimeStartDateTimeFinish(
                                                                              EPriority namePriority, Pageable pageable,
                                                                              LocalDateTime dateTimeStart,
                                                                              LocalDateTime dateTimeFinish) {

        PriorityEntity priority = priorityEntityService.getPriorityByName(namePriority);

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

        return taskEntities;
    }

    public List<TaskEntity> getAllByAuthUser() {
        String username = getAuthentication().getUsername();
        Long idUser = userEntityService.getUsersUserName(username).getIdUser();

        return taskEntityRepository.findAllByUserEntity_IdUser(idUser);
    }

    public List<TaskEntity> getAllByPriorityIdAndUserEntityIdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
                                                                                           Long idPriority,
                                                                                           LocalDateTime dateTimeStart,
                                                                                           LocalDateTime dateTimeFinish,
                                                                                           Pageable pageable) {

        String username = getAuthentication().getUsername();
        Long idUser = userEntityService.getUsersUserName(username).getIdUser();

     return taskEntityRepository.findAllByPriority_IdAndUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
            idPriority, idUser, dateTimeStart, dateTimeFinish, pageable
        ).getContent();

    }

    public List<TaskEntity> getAllByAuthUserAndPriority(Long idPriority, Pageable pageable) {
        String username = getAuthentication().getUsername();
        Long idUser = userEntityService.getUsersUserName(username).getIdUser();

        return taskEntityRepository.findAllByPriority_IdAndUserEntity_IdUser(idPriority, idUser, pageable);
    }

    public Page<TaskEntity> getAllByUserIdDateTimeStartAfterAndDateTimeFinishBefore(LocalDateTime dateTimeStart,
                                                                                    LocalDateTime dateTimeFinish,
                                                                                    Pageable pageable) {
        String username = getAuthentication().getUsername();
        Long idUser = userEntityService.getUsersUserName(username).getIdUser();

        return taskEntityRepository.findAllByUserEntity_IdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
              idUser,  dateTimeStart, dateTimeFinish, pageable
        );
    }
}
