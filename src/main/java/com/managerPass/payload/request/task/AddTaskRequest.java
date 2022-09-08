package com.managerPass.payload.request.task;

import com.managerPass.jpa.entity.PriorityEntity;
import com.managerPass.jpa.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddTaskRequest {

    @Size(min = 1, max = 255)
    private String name;

    @Size(min = 1, max = 255)
    private String message;

    @NotNull
    private UserEntity userEntity;

    private PriorityEntity priority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTimeFinish;
}
