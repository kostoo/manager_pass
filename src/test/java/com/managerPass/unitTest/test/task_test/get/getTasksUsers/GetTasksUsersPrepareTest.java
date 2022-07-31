package com.managerPass.unitTest.test.task_test.get.getTasksUsers;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

public class GetTasksUsersPrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate() {
        return taskServiceProvider.taskGenerate("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN, true);
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate("kosto", "test@test.ru", ERole.ROLE_ADMIN, true);
    }
}
