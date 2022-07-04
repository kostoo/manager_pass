package com.managerPass.unitTest.taskProviderTest.prepareTest;

import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.PriorityEntity;
import com.managerPass.entity.RoleEntity;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import com.managerPass.unitTest.taskProviderTest.TaskProviderGenerationObject;

public class TaskProviderPrepareTest extends PrepareServiceTest {

  protected TaskEntity taskEntityGenerate(Boolean addInBD) {
    PriorityEntity priority = priorityEntityRepository.save(TaskProviderGenerationObject.priorityGenerate());

    RoleEntity roleEntity = roleRepository.save(TaskProviderGenerationObject.roleGenerate(ERole.ROLE_ADMIN));

    UserEntity userEntity = userEntityRepository.save(TaskProviderGenerationObject.userEntityGeneration(roleEntity));

    TaskEntity taskEntity;
    if (addInBD) {
      taskEntity = taskEntityRepository.save(TaskProviderGenerationObject.taskEntityGeneration(priority, userEntity));
    } else {
      taskEntity = TaskProviderGenerationObject.taskEntityGeneration(priority, userEntity);
    }

    return  taskEntity;
  }

}
