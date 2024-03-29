package com.managerPass.service;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.service.TaskRepositoryService;
import com.managerPass.payload.request.task.AddTaskRequest;
import com.managerPass.payload.request.task.UpdateTaskRequest;
import com.managerPass.payload.response.TaskResponse;
import com.managerPass.util.TaskConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskConverter taskConverter;
    private final TaskRepositoryService taskRepositoryService;

    public List<TaskResponse> getAll(String name) {
        return taskConverter.convertListTaskEntityToTaskResponse(taskRepositoryService.getAll(name));
    }

    public TaskResponse getById(Long id) {
        return taskConverter.taskResponseGenerate(taskRepositoryService.getTaskById(id));
    }

    public TaskResponse addTask(AddTaskRequest addTaskRequest) {
        return taskConverter.taskResponseGenerate(taskRepositoryService.addTask(addTaskRequest));
    }

    public TaskResponse updateTask(UpdateTaskRequest taskRequest, Long idTask) {
        return taskConverter.taskResponseGenerate(taskRepositoryService.updateTask(taskRequest, idTask));
    }

    public List<TaskResponse> getAllByUserByNamePriorityOrDateTimeStartAndDateTimeFinish(EPriority namePriority,
                                                                                         Pageable pageable,
                                                                                         LocalDateTime dateTimeStart,
                                                                                         LocalDateTime dateTimeFinish) {

        return taskConverter.convertListTaskEntityToTaskResponse(
                taskRepositoryService.getAllByAuthUserByEPriorityOrDateTimeStartAndDateTimeFinish(
                        namePriority, pageable, dateTimeStart, dateTimeFinish
                )
        );
    }
}
