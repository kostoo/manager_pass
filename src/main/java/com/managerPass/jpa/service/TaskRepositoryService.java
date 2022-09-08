package com.managerPass.jpa.service;

import com.managerPass.exception.CustomRestExceptionHandler;
import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.jpa.repository.TaskEntityRepository;
import com.managerPass.payload.request.task.AddTaskRequest;
import com.managerPass.payload.request.task.UpdateTaskRequest;
import com.managerPass.util.AuthenticationUserUtil;
import com.managerPass.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
    private final PageUtil pageUtil;

    public List<TaskEntity> getAll(String name) {;
        return taskEntityRepository.findAllByName(name);
    }

    public TaskEntity getTaskById(Long id) {
        return taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task not found by Id : %x", id))
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteByIdTask(Long id) {
        getTaskById(id);
        taskEntityRepository.deleteById(id);
    }

    public TaskEntity addTask(AddTaskRequest addTaskRequest) {
        try {
            TaskEntity task = TaskEntity.builder().name(addTaskRequest.getName())
                                                  .message(addTaskRequest.getMessage())
                                                  .dateTimeFinish(addTaskRequest.getDateTimeFinish())
                                                  .dateTimeStart(addTaskRequest.getDateTimeStart())
                                                  .build();

            String username = authenticationUserUtil.getAuthentication().getUsername();
            task.setUserEntity(userRepositoryService.getUserByUsername(username));

            if (task.getPriority() != null) {
                task.setPriority(priorityRepositoryService.getPriorityByName(task.getPriority().getName()));
            } else {
                task.setPriority(priorityRepositoryService.getPriorityByName(EPriority.MEDIUM));
            }

            return taskEntityRepository.save(task);

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TaskEntity updateTask(UpdateTaskRequest taskRequest, Long idTask) {
        try {

            TaskEntity task = getTaskById(idTask);

            if (taskRequest.getName() != null) {
                task.setName(taskRequest.getName());
            }
            if (taskRequest.getMessage() != null) {
                task.setMessage(taskRequest.getMessage());
            }
            if (taskRequest.getUserEntity() != null) {
                task.setUserEntity(taskRequest.getUserEntity());
            }
            if (taskRequest.getPriority() != null) {
                task.setPriority(taskRequest.getPriority());
            }
            if (taskRequest.getDateTimeStart() != null) {
                task.setDateTimeStart(taskRequest.getDateTimeStart());
            }
            if (taskRequest.getDateTimeFinish() != null) {
                task.setDateTimeFinish(taskRequest.getDateTimeFinish());
            }

            return taskEntityRepository.save(task);

        } catch (ConstraintViolationException e) {
            log.warn(e.getClass().getName(), CustomRestExceptionHandler.handleConstraintViolation(e));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public List<TaskEntity> getAllByAuthUserByEPriorityOrDateTimeStartAndDateTimeFinish(
                                                                              EPriority namePriority, Pageable pageable,
                                                                              LocalDateTime dateTimeStart,
                                                                              LocalDateTime dateTimeFinish) {

        String username = authenticationUserUtil.getAuthentication().getUsername();
        Long idPriority = priorityRepositoryService.getPriorityByName(namePriority).getId();
        Long idUser = userRepositoryService.getUserByUsername(username).getIdUser();

        return pageUtil.convertPageToList(
                    taskEntityRepository.findAllByUserEntityIdUserAndPriorityIdOrDateTimeStartAndDateTimeFinish(
                              idPriority, idUser, dateTimeStart, dateTimeFinish, pageable
       ));
    }

}
