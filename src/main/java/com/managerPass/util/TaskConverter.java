package com.managerPass.util;

import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.payload.response.TaskResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskConverter {

    public TaskResponse taskResponseGenerate(TaskEntity taskEntity) {
        return TaskResponse.builder().idTask(taskEntity.getIdTask())
                                     .name(taskEntity.getName())
                                     .message(taskEntity.getMessage())
                                     .priority(taskEntity.getPriority())
                                     .userEntity(taskEntity.getUserEntity())
                                     .dateTimeStart(taskEntity.getDateTimeStart())
                                     .dateTimeFinish(taskEntity.getDateTimeFinish()).build();
    }

    public List<TaskResponse> convertListTaskEntityToTaskResponse(List<TaskEntity> taskEntities) {
        return taskEntities.stream().map(this::taskResponseGenerate).collect(Collectors.toList());
    }
}
