package com.managerPass.unitTest.provider;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.provider.repository.PriorityProvider;
import com.managerPass.unitTest.provider.repository.TaskProvider;
import com.managerPass.unitTest.util.ObjectGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.managerPass.unitTest.util.ObjectGeneratorUtil.taskEntityGeneration;

@Service
public class TaskServiceProvider {

    @Autowired
    private TaskProvider taskProvider;

    @Autowired
    private PriorityProvider priorityProvider;

    @Autowired
    private UserServiceProvider userServiceProvider;

    public TaskEntity taskGenerate(String name , String message, EPriority ePriority,
                                      ERole eRole, Boolean addInDb) {

        PriorityEntity priorityEntity = priorityProvider.save(ObjectGeneratorUtil.priorityGenerate(ePriority));

        TaskEntity task = taskEntityGeneration(
           name, message, priorityEntity, userServiceProvider.userGenerate(
                   "test", "test@test.ru", eRole, "nikita","lastname", true
        ));
        if (addInDb) {
            return taskProvider.save(task);
        } else {
            return task;
        }
    }

    public TaskEntity taskGenerate(String name , String message, EPriority ePriority, UserEntity user, Boolean addInDb) {

        PriorityEntity priorityEntity = priorityProvider.save(ObjectGeneratorUtil.priorityGenerate(ePriority));

        TaskEntity task = taskEntityGeneration(name, message, priorityEntity, user);
        if (addInDb) {
            return taskProvider.save(task);
        } else {
            return task;
        }
    }

}
