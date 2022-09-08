package com.managerPass.test.task.get.list.tasks_name;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.prepateTest.PrepareServiceTest;

public class GetListTasksNamePrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate() {
        return taskProvider.taskGenerateDb("getTasksName", "taskNameMessage", EPriority.HIGH, ERole.ROLE_ADMIN);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname");
    }

}
