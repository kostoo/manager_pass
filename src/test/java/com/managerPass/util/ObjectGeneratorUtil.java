package com.managerPass.util;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.payload.request.SignupRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class ObjectGeneratorUtil {

    public static final LocalDateTime localDateTimeFinishPlus1Month = LocalDateTime.of(LocalDate.of(
         LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth()+1
    ), LocalTime.now());

    public static SignupRequest signupRequestGenerate(Set<ERole> eRoles) {

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(RandomStringUtils.random(10, true, false));
        signupRequest.setRole(eRoles);
        signupRequest.setEmail("test@test.ru");
        signupRequest.setPassword("password");

        return signupRequest;
    }

    public static RoleEntity roleGenerate(ERole eRole) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(eRole);

        return roleEntity;
    }

    public static TaskEntity taskEntityGeneration(String name , String message, PriorityEntity priority,
                                                  UserEntity userEntity) {
        return TaskEntity.builder()
                         .name(name)
                         .message(message)
                         .dateTimeStart(LocalDateTime.now())
                         .dateTimeFinish(localDateTimeFinishPlus1Month)
                         .priority(priority)
                         .userEntity(userEntity)
                         .build();
    }

    public static UserEntity userEntityGeneration(String username, String email, RoleEntity roleEntity, String name,
                                                  String lastName) {
        return  UserEntity.builder()
                          .name(name)
                          .lastName(lastName)
                          .username(username)
                          .email(email)
                          .roles(Set.of(roleEntity))
                          .isAccountNonBlock(true)
                          .isAccountActive(true)
                          .build();
    }

    public static PriorityEntity priorityGenerate(EPriority ePriority) {
        PriorityEntity priority = new PriorityEntity();
        priority.setName(ePriority);

        return priority;
    }
}
