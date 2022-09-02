package com.managerPass.jpa.repository_service;

import com.managerPass.exception.CustomRestExceptionHandler;
import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.jpa.repository.TaskEntityRepository;
import com.managerPass.payload.request.task.AddTaskRequest;
import com.managerPass.payload.request.task.UpdateTaskRequest;
import com.managerPass.util.AuthenticationUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskRepositoryService {

    private final TaskEntityRepository taskEntityRepository;
    private final UserRepositoryService userRepositoryService;
    private final PriorityRepositoryService priorityRepositoryService;
    private final AuthenticationUserUtil authenticationUserUtil;


    public List<TaskEntity> getAll(String name) {;
        return taskEntityRepository.findAllByName(name);
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

    public TaskEntity addTask(AddTaskRequest addTaskRequest) {
        try {
            TaskEntity taskEntity = TaskEntity.builder().name(addTaskRequest.getName())
                                                        .message(addTaskRequest.getMessage())
                                                        .userEntity(addTaskRequest.getUserEntity())
                                                        .priority(addTaskRequest.getPriority())
                                                        .dateTimeFinish(addTaskRequest.getDateTimeFinish())
                                                        .dateTimeStart(addTaskRequest.getDateTimeStart())
                                                        .build();

            String username = authenticationUserUtil.getAuthentication().getUsername();
            taskEntity.setUserEntity(userRepositoryService.getUserByUsername(username));

            if (taskEntity.getPriority() != null) {
                EPriority priorityName = taskEntity.getPriority().getName();
                taskEntity.setPriority(priorityRepositoryService.getPriorityByName(priorityName));
            } else {
                taskEntity.setPriority(priorityRepositoryService.getPriorityByName(EPriority.MEDIUM));
            }

            return taskEntityRepository.save(taskEntity);

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public TaskEntity updateTask(UpdateTaskRequest taskRequest, Long idTask) {
        try {
            if (taskEntityRepository.existsById(idTask)) {
               return taskEntityRepository.save(
                       TaskEntity.builder().idTask(idTask)
                                           .name(taskRequest.getName())
                                           .message(taskRequest.getMessage())
                                           .userEntity(taskRequest.getUserEntity())
                                           .priority(taskRequest.getPriority())
                                           .dateTimeStart(taskRequest.getDateTimeStart())
                                           .dateTimeFinish(taskRequest.getDateTimeFinish())
                                           .build()
               );
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

        String username = authenticationUserUtil.getAuthentication().getUsername();

        return taskEntityRepository.findAllByUserEntityIdUserAndPriorityIdOrDateTimeStartAndDateTimeFinish(
                priorityRepositoryService.getPriorityByName(namePriority).getId(),
                userRepositoryService.getUserByUsername(username).getIdUser(),
                dateTimeStart, dateTimeFinish, pageable
        ).getContent();
    }

}
