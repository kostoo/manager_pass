package com.managerPass.payload.request;

import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.UserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TaskRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String message;

    private UserEntity userEntity;

    private PriorityEntity priority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime dateTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime dateTimeFinish;

}
