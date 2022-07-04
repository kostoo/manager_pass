package com.managerPass.service;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.repository.PriorityEntityRepository;
import com.managerPass.repository.TaskEntityRepository;
import com.managerPass.repository.UserEntityRepository;
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
        }
        return null;
    }

    public List<TaskEntity> getAll() {
        return taskEntityRepository.findAll();
    }

    public List<TaskEntity> getAllByName(String name) {
        return taskEntityRepository.findAllByName(name);
    }

    public List<TaskEntity> getAllWithParam(String name) {
        List<TaskEntity> taskEntities;
        if (name != null) {
            taskEntities = getAllByName(name);
        } else {
            taskEntities = getAll();
        }
        return taskEntities;
    }
    public TaskEntity getByIdTask(Long id) {
        return taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task not found by Id : %x", id))
        );
    }

    public void deleteByIdTask(Long id) {
        taskEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Task not found by Id : %x", id))
        );
        taskEntityRepository.deleteById(id);
    }

    public TaskEntity addTask(TaskRequest taskRequest) {
        TaskEntity taskEntity = TaskEntity.builder().name(taskRequest.getName())
                                                    .message(taskRequest.getMessage())
                                                    .userEntity(taskRequest.getUserEntity())
                                                    .priority(taskRequest.getPriority())
                                                    .dateTimeFinish(taskRequest.getDateTimeFinish())
                                                    .dateTimeStart(taskRequest.getDateTimeStart())
                                                    .build();

        String username = getAuthentication().getUsername();
        taskEntity.setUserEntity(userEntityRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", username))
        ));

        if (taskEntity.getPriority() != null) {
            EPriority priorityName = taskEntity.getPriority().getName();
            taskEntity.setPriority(priorityEntityRepository.findByName(priorityName).orElseThrow(() ->
                new ResponseStatusException(
                      HttpStatus.NOT_FOUND, String.format(" Not found priority name %s ", priorityName)
                ))
            );
        } else {
            taskEntity.setPriority(priorityEntityRepository.findByName(EPriority.MEDIUM).orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND, String.format(" Not found priority name %s", EPriority.MEDIUM)
                    )
            ));
        }
        return taskEntityRepository.save(taskEntity);
    }

    public TaskEntity updateTask(TaskEntity taskEntity) {
        return taskEntityRepository.save(taskEntity);
    }

    public List<TaskEntity> getAllByAuthUserWithParam(Long idPriority,
                                                      Pageable pageable,
                                                      LocalDateTime dateTimeStart,
                                                      LocalDateTime dateTimeFinish) {
        List<TaskEntity> taskEntities;

        if (idPriority != null && dateTimeStart == null && dateTimeFinish == null ) {
            taskEntities = getAllByAuthUserAndPriority(idPriority, pageable);
        }
        else if (idPriority == null && dateTimeStart != null && dateTimeFinish != null) {
            taskEntities = getAllByUserIdDateTimeStartAfterAndDateTimeFinishBefore(
                                        dateTimeStart, dateTimeFinish, pageable
                           ).getContent();
        }
        else if (idPriority != null && dateTimeStart != null && dateTimeFinish != null ) {
            taskEntities = getAllByPriorityIdAndUserEntityIdUserAndDateTimeStartIsAfterAndDateTimeFinishBefore(
                   idPriority, dateTimeStart, dateTimeFinish, pageable
            );
        }
        else {
            taskEntities = getAllByAuthUser();
        }

        return taskEntities;
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
