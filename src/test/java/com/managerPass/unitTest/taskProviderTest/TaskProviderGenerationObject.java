package com.managerPass.unitTest.taskProviderTest;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class TaskProviderGenerationObject {

    public static LocalDateTime localDateTimeFinishPlus1Month = LocalDateTime.of(LocalDate.of(
            LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth()+1
    ), LocalTime.now());

    public static TaskEntity taskEntityGeneration(PriorityEntity priority, UserEntity userEntity) {
        return TaskEntity.builder()
                .name("testtask")
                .message("message")
                .dateTimeStart(LocalDateTime.now())
                .dateTimeFinish(localDateTimeFinishPlus1Month)
                .priority(priority)
                .userEntity(userEntity)
                .build();
    }

    public static UserEntity userEntityGeneration(RoleEntity roleEntity) {
        return  UserEntity.builder()
                          .username("kosto")
                          .email("test@gmail.com")
                          .roles(Set.of(roleEntity))
                          .isAccountNonBlock(true)
                          .isAccountActive(true)
                          .build();
    }

    public static RoleEntity roleGenerate(ERole eRole) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(eRole);

        return roleEntity;
    }

    public static PriorityEntity priorityGenerate() {
        PriorityEntity priority = new PriorityEntity();
        priority.setName(EPriority.MEDIUM);

        return priority;
    }
}
