package com.managerPass.payload.response;

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
public class TaskResponse {

    @NotNull
    private Long idTask;

    @Size(min = 5)
    private String name;

    @Size(min = 3)
    private String message;

    @NotNull
    private UserEntity userEntity;

    @NotNull
    private PriorityEntity priority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime dateTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime dateTimeFinish;

}
