package com.managerPass.util;

import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.PriorityEntity;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.jpa.entity.UserEntity;
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

    public static TaskEntity taskEntityGeneration(String name, String message, PriorityEntity priority,
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
}
