package com.managerPass.service;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.repository_service.TaskRepositoryService;
import com.managerPass.payload.request.TaskRequest;
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

    public TaskResponse getByIdTask(Long id) {
        return taskConverter.taskResponseGenerate(taskRepositoryService.getByIdTask(id));
    }

    public TaskResponse addTask(TaskRequest taskRequest) {
        return taskConverter.taskResponseGenerate(taskRepositoryService.addTask(taskRequest));
    }

    public TaskResponse updateTask(TaskRequest taskRequest, Long idTask) {
        return taskConverter.taskResponseGenerate(taskRepositoryService.updateTask(taskRequest, idTask));
    }

    public List<TaskResponse> getAllByAuthUserWithNamePriorityPageableDateTimeStartDateTimeFinish(
                                                                              EPriority namePriority, Pageable pageable,
                                                                              LocalDateTime dateTimeStart,
                                                                              LocalDateTime dateTimeFinish) {

        return taskConverter.convertListTaskEntityToTaskResponse(
                taskRepositoryService.getAllByAuthUserWithEPriorityPageableDateTimeStartDateTimeFinish(
                        namePriority, pageable, dateTimeStart, dateTimeFinish
                )
        );
    }
}
