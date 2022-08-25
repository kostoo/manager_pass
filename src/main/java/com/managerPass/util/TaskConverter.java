package com.managerPass.util;

import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.payload.response.TaskResponse;

import java.util.List;
import java.util.stream.Collectors;

public class TaskConverter {

    public static TaskEntity taskEntityGenerate(TaskRequest taskRequest, Long idTask) {
        return TaskEntity.builder().idTask(idTask)
                                   .name(taskRequest.getName())
                                   .message(taskRequest.getMessage())
                                   .userEntity(taskRequest.getUserEntity())
                                   .priority(taskRequest.getPriority())
                                   .dateTimeStart(taskRequest.getDateTimeStart())
                                   .dateTimeFinish(taskRequest.getDateTimeFinish()).build();
    }

    public static TaskResponse taskResponseGenerate(TaskEntity taskEntity) {
        return TaskResponse.builder().idTask(taskEntity.getIdTask())
                                     .name(taskEntity.getName())
                                     .message(taskEntity.getMessage())
                                     .priority(taskEntity.getPriority())
                                     .userEntity(taskEntity.getUserEntity())
                                     .dateTimeStart(taskEntity.getDateTimeStart())
                                     .dateTimeFinish(taskEntity.getDateTimeFinish()).build();
    }

    public static TaskEntity taskEntityGenerate(TaskRequest taskRequest) {
        return TaskEntity.builder().name(taskRequest.getName())
                                   .message(taskRequest.getMessage())
                                   .userEntity(taskRequest.getUserEntity())
                                   .priority(taskRequest.getPriority())
                                   .dateTimeFinish(taskRequest.getDateTimeFinish())
                                   .dateTimeStart(taskRequest.getDateTimeStart())
                                   .build();
    }

    public static TaskRequest taskRequestGenerate(TaskEntity taskEntity) {
        return TaskRequest.builder().name(taskEntity.getName())
                                    .message(taskEntity.getMessage())
                                    .dateTimeStart(taskEntity.getDateTimeStart())
                                    .dateTimeFinish(taskEntity.getDateTimeFinish())
                                    .userEntity(taskEntity.getUserEntity())
                                    .priority(taskEntity.getPriority())
                                    .build();
    }

    public static List<TaskResponse> convertListTaskEntityToTaskResponse(List<TaskEntity> taskEntities) {
        return taskEntities.stream().map(TaskConverter::taskResponseGenerate).collect(Collectors.toList());
    }
}
