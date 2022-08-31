package com.managerPass.provider;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
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
