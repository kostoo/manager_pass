package com.managerPass.service;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.payload.response.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.managerPass.util.TaskConverter.convertListTaskEntityToTaskResponse;
import static com.managerPass.util.TaskConverter.taskResponseGenerate;

@Service
@RequiredArgsConstructor
public class TaskResponseService {

    private final TaskEntityService taskEntityService;

    public List<TaskResponse> getAllName(String name) {
        return convertListTaskEntityToTaskResponse(taskEntityService.getAllName(name));
    }

    public TaskResponse getByIdTask(Long id) {
        return taskResponseGenerate(taskEntityService.getByIdTask(id));
    }

    public TaskResponse addTask(TaskRequest taskRequest) {
        return taskResponseGenerate(taskEntityService.addTask(taskRequest));
    }

    public TaskResponse updateTask(TaskRequest taskRequest, Long idTask) {
        return taskResponseGenerate(taskEntityService.updateTask(taskRequest, idTask));
    }

    public List<TaskResponse> getAllByAuthUserWithNamePriorityPageableDateTimeStartDateTimeFinish(
                                                                              EPriority namePriority, Pageable pageable,
                                                                              LocalDateTime dateTimeStart,
                                                                              LocalDateTime dateTimeFinish) {

        return convertListTaskEntityToTaskResponse(
                taskEntityService.getAllByAuthUserWithEPriorityPageableDateTimeStartDateTimeFinish(
                        namePriority, pageable, dateTimeStart, dateTimeFinish
                )
        );
    }
}
