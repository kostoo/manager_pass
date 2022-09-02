package com.managerPass.provider;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.PriorityEntity;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.provider.repository.TaskRepositoryTest;
import com.managerPass.util.ObjectGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskProvider {

    @Autowired
    private TaskRepositoryTest taskRepositoryTest;

    @Autowired
    private PriorityProvider priorityProvider;

    @Autowired
    private UserProvider userProvider;

    public TaskRequest taskRequestGenerate(TaskEntity taskEntity) {
        return TaskRequest.builder().name(taskEntity.getName())
                                    .message(taskEntity.getMessage())
                                    .dateTimeStart(taskEntity.getDateTimeStart())
                                    .dateTimeFinish(taskEntity.getDateTimeFinish())
                                    .userEntity(taskEntity.getUserEntity())
                                    .priority(taskEntity.getPriority())
                                    .build();
    }

    public TaskEntity taskGenerate(String name, String message, EPriority ePriority, ERole eRole, Boolean addInDb) {

        PriorityEntity priorityEntity = priorityProvider.priorityGenerate(ePriority);

        TaskEntity task = ObjectGeneratorUtil.taskEntityGeneration(
           name, message, priorityEntity, userProvider.userGenerate(
                   "test", "test@test.ru", eRole, "nikita","lastname", true
                        )
        );

        if (addInDb) {
            return taskRepositoryTest.save(task);
        } else {
            return task;
        }
    }

    public TaskEntity taskGenerate(String name, String message, EPriority ePriority, UserEntity user, Boolean addInDb) {

        PriorityEntity priorityEntity = priorityProvider.priorityGenerate(ePriority);

        TaskEntity task = ObjectGeneratorUtil.taskEntityGeneration(name, message, priorityEntity, user);

        if (addInDb) {
            return taskRepositoryTest.save(task);
        } else {
            return task;
        }
    }

    public Boolean existTaskById(Long id) {
        return taskRepositoryTest.existsById(id);
    }
}
